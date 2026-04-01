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
                "-Dexecution=${params.EXECUTION}",
                "-Dcucumber.filter.tags=${params.SCENARIO_TAG}",
                "-Dusername=${env.APP_USERNAME}",
                "-Dpassword=${env.APP_PASSWORD}",
                "-Dthreads=${params.THREADS}",
                "-DexplicitWait=${params.EXPLICIT_WAIT}",
                "-Dbranch=${branch}",
                "-Dcommit=${commit}"
        ]

        def browserStackParamsList = []

        if (params.EXECUTION == "browserstack") {
            browserStackParamsList = [
                    "-Dbs.username=${env.BS_USERNAME}",
                    "-Dbs.accessKey=${env.BS_ACCESS_KEY}",
                    "-Dbs.url=https://hub-cloud.browserstack.com/wd/hub",
                    "-Dbs.app=${env.BS_APP}",
                    "-Dbs.projectName=Mobile-Automation-Framework",
                    "-Dbs.buildName=Jenkins-${env.BUILD_NUMBER}-${deviceId}",
                    "-Dbs.video=true",
                    "-Dbs.deviceLogs=true",
                    "-Dbs.appiumLogs=true",
                    "-Dbs.networkLogs=true",
                    "-Dbs.appiumVersion=2.0.1"
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

        parallelStages[deviceId] = {

            stage("Run ${currentDevice.deviceName}") {

                def allParams = (coreParamsList + browserStackParamsList + deviceParamsList)
                        .collect { it.contains(" ") ? "\"${it}\"" : it }
                        .join(" ")

                try {

                    sh """
                    ./gradlew clean executeFeatures \
                    ${gradleFlags} \
                    ${allParams}
                    """

                } catch (err) {

                    echo "Retry failed scenarios for ${deviceId}"

                    sh """
                    ./gradlew reExecuteFeatures \
                    ${gradleFlags} \
                    ${allParams}
                    """
                }

                archiveArtifacts artifacts: "${buildDir}/allure-results/**", allowEmptyArchive: true
                archiveArtifacts artifacts: "build/logs/**", allowEmptyArchive: true
            }
        }
    }

    parallelStages.failFast = false
    parallel parallelStages
}
