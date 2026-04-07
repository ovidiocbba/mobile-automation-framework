import groovy.json.JsonSlurper

def call(String device, String execution) {

    def routerPath = "/var/jenkins_home/resources/devices/devices.json"
    def router = new JsonSlurper().parse(new File(routerPath))

    def devices = new JsonSlurper().parse(new File(router[execution]))

    if (device == "ALL") {
        return devices
    }

    return devices.findAll { it.deviceName == device }
}
