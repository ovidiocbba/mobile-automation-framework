def normalize(String value) {
    return value
            ?.toLowerCase()
            ?.replaceAll(" ", "-")
            ?.replaceAll("[^a-z0-9-]", "")
}
