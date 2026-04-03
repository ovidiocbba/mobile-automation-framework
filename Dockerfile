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

ENV JAVA_17_HOME=/opt/java/openjdk17

RUN mkdir -p $JAVA_17_HOME && \
    curl -L https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.10%2B7/OpenJDK17U-jdk_x64_linux_hotspot_17.0.10_7.tar.gz | tar -xzC $JAVA_17_HOME --strip-components=1 && \
    mkdir -p /usr/lib/jvm && \
    ln -s /opt/java/openjdk17 /usr/lib/jvm/java-17-openjdk-amd64

# Set system language and encoding to English and UTF-8
ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

RUN echo "===== JAVA VERSION =====" && java -version && \
    echo "===== GIT VERSION =====" && git --version && \
    echo "===== LOCALE =====" && locale

# Install Allure CLI
ENV ALLURE_VERSION=2.25.0

RUN curl -fsSL https://github.com/allure-framework/allure2/releases/download/${ALLURE_VERSION}/allure-${ALLURE_VERSION}.tgz -o allure.tgz && \
    tar -zxf allure.tgz -C /opt/ && \
    ln -s /opt/allure-${ALLURE_VERSION}/bin/allure /usr/bin/allure && \
    rm allure.tgz && \
    echo "===== ALLURE VERSION =====" && allure --version

# Copy plugins list (managed separately)
COPY jenkins/plugins.txt /usr/share/jenkins/ref/plugins.txt

# Install plugins using plugin file
RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

# Enable Jenkins Configuration as Code (JCasC)
ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc_configs

# Create configuration folder and copy JCasC configuration file
RUN mkdir -p /var/jenkins_home/casc_configs && \
    chown -R jenkins:jenkins /var/jenkins_home/casc_configs

# Copy JCasC configuration file
COPY jenkins/jenkins.yaml /var/jenkins_home/casc_configs/jenkins.yaml

# Set Java encoding to UTF-8 for Jenkins logs and disable Jenkins Content Security Policy so HTML reports like Allure can load JS/CSS
ENV JAVA_OPTS="-Dfile.encoding=UTF-8 -Dhudson.model.DirectoryBrowserSupport.CSP="

# Switch back to secure Jenkins user
USER jenkins
