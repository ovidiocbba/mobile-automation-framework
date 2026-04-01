import groovy.json.JsonSlurper

/**
 * Returns the list of devices to execute.
 * Requires SUPPORTED_DEVICES env variable (JSON format)
 */
def call(String device) {

    if (!env.SUPPORTED_DEVICES?.trim()) {
        error """
        SUPPORTED_DEVICES environment variable is not defined.

        Example:
        -e SUPPORTED_DEVICES='[
          {
            "deviceName": "Samsung Galaxy S23",
            "platformVersion": "13",
            "platform": "ANDROID",
            "automationName": "UiAutomator2"
          }
        ]'
        """
    }

    // PARSE JSON
    def rawDevices

    try {
        rawDevices = new JsonSlurper().parseText(env.SUPPORTED_DEVICES)
    } catch (Exception e) {
        error "Invalid JSON in SUPPORTED_DEVICES: ${e.message}"
    }

    if (!rawDevices || rawDevices.isEmpty()) {
        error "SUPPORTED_DEVICES is empty."
    }

    // LazyMap -> HashMap (serializable)
    def allDevices = rawDevices.collect { d ->
        [
                deviceName: d.deviceName,
                platformVersion: d.platformVersion,
                platform: d.platform,
                automationName: d.automationName
        ]
    }

    allDevices.each { d ->
        if (!d.deviceName?.trim() ||
                !d.platform?.trim() ||
                !d.platformVersion?.trim() ||
                !d.automationName?.trim()) {

            error "Invalid device config detected: ${d}"
        }
    }

    echo "Devices loaded: ${allDevices*.deviceName}"

    // FILTER LOGIC
    if (device == 'ALL') {
        return allDevices
    }

    def filtered = allDevices.findAll { it.deviceName == device }

    if (!filtered) {
        error "Device '${device}' not found in SUPPORTED_DEVICES"
    }

    return filtered
}
