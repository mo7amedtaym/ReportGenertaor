package utiity

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.AllData

fun generateJson(data: AllData): String {
    return Json { prettyPrint = true }.encodeToString(data)

}