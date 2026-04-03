def call(String execution) {

    validate(env.APP_USERNAME, "USERNAME")
    validate(env.APP_PASSWORD, "USERNAME")

    if (execution == "browserstack") {
        validate(env.BS_USERNAME, "BS_USERNAME")
        validate(env.BS_ACCESS_KEY, "BS_ACCESS_KEY")
        validate(env.BS_APP, "BS_APP")
    }

    echo "Credentials validated"
}

def validate(value, id) {

    if (!value || value == "replace-me") {
        error """
Credential '${id}' is not configured.

Go to:
Manage Jenkins → Credentials → Global
"""
    }

    if (value.trim() != value) {
        error "Credential '${id}' contains leading or trailing spaces"
    }

}
