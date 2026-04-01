def call(String execution) {

    try {

        def creds = [
                usernamePassword(
                        credentialsId: 'USERNAME',
                        usernameVariable: 'USERNAME',
                        passwordVariable: 'PASSWORD'
                )
        ]

        if (execution == "browserstack") {

            creds += [
                    string(credentialsId: 'BS_USERNAME', variable: 'BS_USERNAME'),
                    string(credentialsId: 'BS_ACCESS_KEY', variable: 'BS_ACCESS_KEY'),
                    string(credentialsId: 'BS_APP', variable: 'BS_APP')
            ]

        }

        withCredentials(creds) {

            echo "Credentials validated successfully"

        }

    } catch (err) {

        error """
        Jenkins Credentials validation failed.

        Missing credentials in Jenkins:

        Add them here:

        Manage Jenkins
         → Credentials
         → System
         → Global
         → Add Credentials

        Required credentials:
        - USERNAME
        - PASSWORD

        BrowserStack required when EXECUTION=browserstack:
        - BS_USERNAME
        - BS_ACCESS_KEY
        - BS_APP

        Error: ${err.message}
        """

    }

}
