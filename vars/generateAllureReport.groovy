def call(devices) {

    def resultPaths = devices.collect { device ->
        def deviceId = device.deviceName
                .toLowerCase()
                .replaceAll(" ", "-")
        "build-${deviceId}/allure-results"
    }

    echo "Generating Allure report..."
    sh "allure generate ${resultPaths.join(' ')} --clean -o allure-report"

    echo "Generating index..."
    sh '''
        chmod +x jenkins/scripts/generate-index.sh
        ./jenkins/scripts/generate-index.sh
    '''
}
