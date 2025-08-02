package utiity

fun applyVariables(template: String, variables: Map<String, String>): String {
    var result = template
    for ((key, value) in variables) {
        result = result.replace("{{${key}}}", value)
    }
    return result
}