/**
 * Execute mobile tests in parallel using BrowserStack or local execution.
 */
def call(devices, params, gradleFlags) {

    def branch = params.BRANCH

    def commit = sh(
            script: "git rev-parse HEAD",
            returnStdout: true
    ).trim()

    def parallelStages = [:]

    for (device in devices) {

        def currentDevice = device

        def deviceId = currentDevice.deviceName
                .toLowerCase()
                .replaceAll(" ", "-")
                .replaceAll("[^a-z0-9-]", "")

        def buildDir = "build-${deviceId}"

        def coreParamsList = [
                "-Dexecution=${currentDevice.type}",
                "-Dcucumber.filter.tags=${params.SCENARIO_TAG}",
                "-Dusername=$APP_USERNAME",
                "-Dpassword=$APP_PASSWORD",
                "-Dthreads=${params.THREADS}",
                "-DexplicitWait=${params.EXPLICIT_WAIT}",
                "-Dbranch=${branch}",
                "-Dcommit=${commit}"
        ]

        def browserStackParamsList = []

        if (currentDevice.type == "browserstack") {
            browserStackParamsList = [
                    "-Dbrowserstack.username=$BROWSERSTACK_USERNAME",
                    "-Dbrowserstack.accessKey=$BROWSERSTACK_ACCESS_KEY",
                    "-Dbrowserstack.url=https://hub-cloud.browserstack.com/wd/hub",
                    "-Dbrowserstack.app=$BROWSERSTACK_APP",
                    "-Dbrowserstack.projectName=Mobile-Automation-Framework",
                    "-Dbrowserstack.buildName=Jenkins-$BUILD_NUMBER-${deviceId}",
                    "-Dbrowserstack.video=true",
                    "-Dbrowserstack.deviceLogs=true",
                    "-Dbrowserstack.appiumLogs=true",
                    "-Dbrowserstack.networkLogs=true",
                    "-Dbrowserstack.appiumVersion=2.0.1"
            ]
        }

        def deviceParamsList = [
                "-Dplatform=${currentDevice.platform}",
                "-DdeviceName=${currentDevice.deviceName}",
                "-DplatformVersion=${currentDevice.platformVersion}",
                "-DautomationName=${currentDevice.automationName}",
                "-Dallure.results.directory=${buildDir}/allure-results",
                "-Dorg.gradle.project.buildDir=${buildDir}"
        ]

        if (currentDevice.type == "local") {
            deviceParamsList += [
                    "-Dudid=${currentDevice.udid}",
                    "-DappiumServerUrl=${currentDevice.appiumServerUrl}"
            ]
        }

        parallelStages[deviceId] = {

            stage("Run ${currentDevice.deviceName}") {

                // Build GRADLE_OPTS to avoid Jenkins insecure interpolation warning with secrets in `sh`
                def gradleOpts = (coreParamsList + browserStackParamsList + deviceParamsList)
                        .collect { it.contains(" ") ? "\"${it}\"" : it }
                        .join(" ")

                try {

                    withEnv(["GRADLE_OPTS=" + gradleOpts]) {
                        sh """
                        ./gradlew executeFeatures ${gradleFlags}
                        """
                    }

                } catch (err) {

                    echo "Retry failed scenarios for ${deviceId}"

                    withEnv(["GRADLE_OPTS=" + gradleOpts]) {
                        sh """
                        ./gradlew reExecuteFeatures ${gradleFlags}
                        """
                    }
                }

                archiveArtifacts artifacts: "${buildDir}/allure-results/**", allowEmptyArchive: true
                archiveArtifacts artifacts: "build/logs/**", allowEmptyArchive: true
            }
        }
    }

    parallelStages.failFast = false
    parallel parallelStages
}
