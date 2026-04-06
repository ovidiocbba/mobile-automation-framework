def call(String execution) {

    validate(env.APP_USERNAME, "APP_USERNAME")
    validate(env.APP_PASSWORD, "APP_PASSWORD")

    if (execution == "browserstack") {
        validate(env.BS_USERNAME, "BS_USERNAME")
        validate(env.BS_ACCESS_KEY, "BS_ACCESS_KEY")
        validate(env.BS_APP, "BS_APP")
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
