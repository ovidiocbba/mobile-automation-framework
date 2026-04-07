import groovy.json.JsonSlurperClassic

def call(String device, String execution) {

    def routerPath = "/var/jenkins_home/resources/devices/devices.json"

    def router = new JsonSlurperClassic().parse(new File(routerPath))
    def devicesRaw = new JsonSlurperClassic().parse(new File(router[execution]))

    // Convert LazyMap -> HashMap
    def devices = devicesRaw.collect { new HashMap(it) }

    if (device == "ALL") {
        return devices
    }

    return devices.findAll { it.deviceName == device }
}
