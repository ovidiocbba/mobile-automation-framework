@Library('shared-pipeline-steps') _

def REPO_URL = env.GIT_REPO
def devices = []

pipeline {
    // Run this pipeline on any available Jenkins agent
    agent any

    tools {
        git "Default" // Use the Git installation named "Default" (as configured in jenkins.yaml)
    }

    options {
        // Disable Jenkins automatic checkout to avoid cloning the repository twice
        skipDefaultCheckout(true)

        // Allows tools like Gradle to display colored output (errors, warnings, info)
        ansiColor('xterm')

        // Global timeout to stop the whole pipeline if it runs too long
        timeout(time: 40, unit: 'MINUTES')

        // Do not allow two builds of this job at the same time
        disableConcurrentBuilds()

        // Add timestamps to Jenkins logs for better debugging
        timestamps()

        // Keep only the latest 20 builds and artifacts from the last 10 builds to save disk space
        buildDiscarder(logRotator(
            numToKeepStr: '20',
            artifactNumToKeepStr: '10'
        ))
    }

    parameters {

        // Browser selection parameter
        string(
            name: 'BRANCH',
            defaultValue: 'main',
            description: 'Git branch to build (example: main, develop, feature/login)'
        )

        choice(
            name: 'EXECUTION',
            choices: ['browserstack','local']
        )

        choice(
            name: 'DEVICE',
            choices: ['ALL', 'Samsung Galaxy S23', 'Google Pixel 7'],
            description: 'Device to execute (ALL | Samsung Galaxy S23 | Google Pixel 7)'
        )

        string(
            name: 'SCENARIO_TAG',
            defaultValue: '@regression',
            description: 'Scenario tag to execute (e.g. "@TC-00001")'
        )

        string(
            name: 'EXPLICIT_WAIT',
            defaultValue: '30',
            description: 'Explicit wait timeout in seconds (e.g. "30")'
        )

        string(
            name: 'THREADS',
            defaultValue: '1',
            description: 'Number of parallel threads for test execution (e.g. "2")'
        )
    }

    environment {

        // Export branch so tests and Allure can read it
        BRANCH = "${params.BRANCH}"

        // Use JDK configured in Jenkins Global Tool Configuration
        JAVA_HOME = tool name: 'jdk17'
        // Add selected JDK to system PATH
        PATH = "${JAVA_HOME}/bin:${env.PATH}"

        // Shared Gradle cache to make builds faster (improves CI performance)
        GRADLE_USER_HOME = "/var/jenkins_home/.gradle"

        // Gradle flags optimized for CI logs
        // --no-daemon: do not start Gradle daemon in CI
        // --console=plain: better log formatting for CI
        // --warning-mode summary: show warning summary only
        GRADLE_FLAGS = "--no-daemon --console=plain --warning-mode summary"

        // Credentials (configure in Jenkins)
        USERNAME = credentials('USERNAME')
        PASSWORD = credentials('PASSWORD')

        BS_USERNAME = credentials('BS_USERNAME')
        BS_ACCESS_KEY = credentials('BS_ACCESS_KEY')
        BS_APP = credentials('BS_APP')
    }

    stages {

        // Clean workspace before build (Gradle cache is kept outside)
        stage('Prepare Workspace') {
            steps {
                cleanWs()
                script {
                    devices = getDevices(params.DEVICE)
                }
            }
        }

        stage('Checkout') {
            steps {
                // Clone repository from Git (retry helps avoid temporary network errors)
                retry(3) {
                    timeout(time: 5, unit: 'MINUTES') {

                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: "*/${params.BRANCH}"]],
                            userRemoteConfigs: [[
                                url: REPO_URL
                            ]]
                        ])

                    }
                }
            }
        }

        stage('Build') {
            steps {
                // Clean workspace and compile project, skipping tests.
                sh "./gradlew clean assemble ${env.GRADLE_FLAGS}"
            }
        }

        stage('Execute Mobile Tests') {

            // Stage timeout to stop this stage if tests take too long
            options {
                timeout(time: 30, unit: 'MINUTES')
            }

            steps {
                // Added to allow pipeline continuation even if tests fail
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    script {
                        runMobileTests(devices, params, env.GRADLE_FLAGS)
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                script {
                    generateAllureReport(devices)
                }
            }
        }

        stage('Publish Report') {
            steps {
                // Publish the full allure-report folder
                publishHTML(target: [
                    reportDir: 'allure-report',       // root folder containing index.html + subreports
                    reportFiles: 'index.html',        // main entry point
                    reportName: 'Mobile-Report',      // display name in Jenkins (no spaces)
                    keepAll: true,                    // keep old builds
                    alwaysLinkToLastBuild: true       // link to latest build
                ])
            }
        }
    }
}
