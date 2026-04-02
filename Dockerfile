# Jenkins LTS with Java 21
# Base image already includes Jenkins and JDK 21
FROM jenkins/jenkins:2.541.3-lts-jdk21

# Switch to root to install system dependencies
USER root

# Install Base System Dependencies, locales and UTF-8 support
ENV DEBIAN_FRONTEND=noninteractive

# First update and install all dependencies
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    curl \
    wget \
    gnupg \
    unzip \
    rsync \
    ca-certificates \
    fonts-liberation \
    libnss3 \
    libxss1 \
    libasound2 \
    libatk-bridge2.0-0 \
    libgtk-3-0 \
    libgbm1 \
    xvfb \
    xauth \
    git \
    locales && \
    \
    echo "en_US.UTF-8 UTF-8" > /etc/locale.gen && \
    locale-gen && \
    update-locale LANG=en_US.UTF-8 LC_ALL=en_US.UTF-8 && \
    \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Set system language and encoding to English and UTF-8
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

RUN echo "===== JAVA VERSION =====" && java -version && \
    echo "===== GIT VERSION =====" && git --version && \
    echo "===== LOCALE =====" && locale

# Install Node (for Allure optional tools)
RUN curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get update && \
    apt-get install -y nodejs

# Install Allure CLI
ENV ALLURE_VERSION=2.25.0

RUN curl -fsSL https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.tgz -o allure.tgz && \
    tar -zxf allure.tgz -C /opt/ && \
    ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/bin/allure && \
    rm allure.tgz && \
    echo "===== ALLURE VERSION =====" && allure --version

# Install Jenkins plugins
RUN jenkins-plugin-cli --plugins \
    workflow-aggregator \
    pipeline-stage-view \
    git \
    credentials-binding \
    allure-jenkins-plugin \
    configuration-as-code \
    job-dsl \
    blueocean \
    ansicolor \
    timestamper \
    ws-cleanup \
    htmlpublisher

# Enable Jenkins Configuration as Code (JCasC)
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs

# Create configuration folder and copy JCasC configuration file
RUN mkdir -p /var/jenkins_home/casc_configs && \
    chown -R jenkins:jenkins /var/jenkins_home/casc_configs

# Copy JCasC configuration file
COPY jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml

# Set Java encoding to UTF-8 for Jenkins logs and disable Jenkins Content Security Policy so HTML reports like Allure can load JS/CSS
ENV JAVA_OPTS="-Dhudson.model.DirectoryBrowserSupport.CSP=sandbox allow-scripts allow-same-origin; default-src 'self'; img-src 'self' data: blob:; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval' blob:;"

# Switch back to secure Jenkins user
USER jenkins
