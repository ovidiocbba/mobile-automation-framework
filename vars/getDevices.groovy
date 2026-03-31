/**
 * Returns the list of devices to execute.
 */
def call(String device) {

    def allDevices = [
            [
               deviceName: 'Samsung Galaxy S23',
               platformVersion: '13',
               platform: 'ANDROID',
               automationName: 'UiAutomator2'
            ],
            [
               deviceName: 'Google Pixel 7',
               platformVersion: '13',
               platform: 'ANDROID',
               automationName: 'UiAutomator2'
            ]
    ]

    if (device == 'ALL') {
        return allDevices
    }

    return allDevices.findAll { it.deviceName == device }
}
