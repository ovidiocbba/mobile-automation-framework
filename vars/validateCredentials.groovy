def call(String execution) {

    validate(env.APP_USERNAME, "APP_USERNAME")
    validate(env.APP_PASSWORD, "APP_PASSWORD")

    if (execution == "browserstack") {
        validate(env.BROWSERSTACK_USERNAME, "BROWSERSTACK_USERNAME")
        validate(env.BROWSERSTACK_ACCESS_KEY, "BROWSERSTACK_ACCESS_KEY")
        validate(env.BROWSERSTACK_APP_ANDROID, "BROWSERSTACK_APP_ANDROID")
        validate(env.BROWSERSTACK_APP_IOS, "BROWSERSTACK_APP_IOS")
    }

    echo "Credentials validated"
}

def validate(value, id) {

    if (!value?.trim()) {
        error "Environment variable '${id}' is not defined or empty"
    }

    if (value.trim() != value) {
        error "Credential '${id}' contains leading or trailing spaces"
    }

}
