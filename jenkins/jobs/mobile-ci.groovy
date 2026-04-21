def JOB_NAME        = 'mobile-ci'
def JOB_DESCRIPTION = 'Mobile Automation CI - Dynamic Devices'

def REPO_URL    = System.getenv("GIT_REPO")
def REPO_BRANCH = System.getenv("GIT_BRANCH")

def DEVICES_ROOT = "/var/jenkins_home/resources/devices/devices.json"

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

        activeChoiceReactiveParam('DEVICE') {
            choiceType('SINGLE_SELECT')
            groovyScript {
                script("""
                    import groovy.json.JsonSlurper

                    def router = new JsonSlurper().parse(
                        new File("${DEVICES_ROOT}")
                    )

                    def devices = new JsonSlurper().parse(
                        new File(router[EXECUTION])
                    )

                    def list = ["ALL"]
                    devices.each { list << it.deviceName }

                    return list
                """)
            }
            referencedParameter('EXECUTION')
        }

        stringParam(
                'SCENARIO_TAG',
                '@regression',
                'Scenario tag to execute (e.g. "@TC-00001")'
        )

        stringParam(
                'EMAILS',
                'qa@company.com',
                'Emails separated by comma'
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
