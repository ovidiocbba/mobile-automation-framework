def JOB_NAME        = 'mobile-ci'
def JOB_DESCRIPTION = 'Mobile Automation CI - Dynamic Devices'

def REPO_URL    = System.getenv("GIT_REPO")
def REPO_BRANCH = System.getenv("GIT_BRANCH")

def devicesJson = System.getenv("SUPPORTED_DEVICES")
def deviceNames = ["ALL"]

if (devicesJson) {
    try {
        new groovy.json.JsonSlurper().parseText(devicesJson).each {
            deviceNames << it.deviceName
        }
    } catch (Exception e) {
        println("Invalid SUPPORTED_DEVICES JSON: ${e.message}")
    }
}

pipelineJob(JOB_NAME) {

    description(JOB_DESCRIPTION)

    /*
    Parameters are defined here so Jenkins shows "Build with Parameters" immediately.
    Otherwise Jenkins only detects Jenkinsfile parameters after the first build (common with Docker restarts).
    */
    parameters {

        stringParam(
                'BRANCH',
                'main',
                'Git branch to build (example: main, develop, feature/login)'
        )

        choiceParam(
                'EXECUTION',
                ['browserstack','local'],
                'Execution type'
        )

        choiceParam(
                'DEVICE',
                deviceNames,
                'Device to execute'
        )

        stringParam(
                'SCENARIO_TAG',
                '@regression',
                'Scenario tag to execute (e.g. "@TC-00001")'
        )

        stringParam(
                'EXPLICIT_WAIT',
                '30',
                'Explicit wait timeout in seconds (e.g. "30")'
        )

        stringParam(
                'THREADS',
                '1',
                'Number of parallel threads for test execution (e.g. "2")'
        )
    }

    // Load Pipeline script from Git (Pipeline as Code)
    definition {
        // cpsScm = CPS (Continuation Passing Style) + SCM (Source Code Management):
        // executes a Pipeline using a Jenkinsfile stored in a Git repository.
        cpsScm {
            // scm = SCM (Source Code Management):
            // defines which repository and branch Jenkins must clone.
            scm {
                git {
                    remote {
                        url(REPO_URL)
                    }
                    branch(REPO_BRANCH)
                }
            }
            // Path to Jenkinsfile inside the repository
            scriptPath('Jenkinsfile')
        }
    }
}
