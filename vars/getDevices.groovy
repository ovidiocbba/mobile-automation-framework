import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def call(String device, String execution) {

    def routerPath = "/var/jenkins_home/resources/devices/devices.json"

    def router = new JsonSlurper().parse(new File(routerPath))
    router = new JsonSlurper().parseText(JsonOutput.toJson(router))

    def devices = new JsonSlurper().parse(new File(router[execution]))
    devices = new JsonSlurper().parseText(JsonOutput.toJson(devices))

    if (device == "ALL") {
        return devices
    }

    return devices.findAll { it.deviceName == device }
}
