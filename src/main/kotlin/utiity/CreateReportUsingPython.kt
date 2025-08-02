package utiity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.AllData
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

suspend fun createDocxUsingPython(templatePath: String, outputPath: String, data: AllData): Boolean {
    // تأكد من تحديث هذا المسار إلى المسار الفعلي لملف Python الخاص بك!
    // Make sure to update this path to the actual path of your Python script!
    val pythonScript = "src\\main\\kotlin\\python\\create_report.py" // مثال على مسار Windows
    // أو على Linux/macOS: "/home/youruser/path/to/your/project/create_police_report.py"

    return withContext(Dispatchers.IO) {
        try {
            val jsonFile = File.createTempFile("investigation_data", ".json")
            val jsonString = Json.encodeToString(data)

            println(jsonString)

            jsonFile.writeText(jsonString)
            val process = ProcessBuilder(
                "python", pythonScript, templatePath, outputPath, jsonFile.absolutePath
            )
                .redirectErrorStream(true) // دمج stderr مع stdout لتسهيل القراءة
                .start()

            val reader = process.inputStream.bufferedReader()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                println("Python Output: $line") // طباعة مخرجات Python إلى console
            }

            val exitCode = process.waitFor() // انتظار انتهاء عملية Python
            if (exitCode == 0) {
                true // نجاح
            } else {
                println("Python script exited with error code: $exitCode")
                false // فشل
            }
        } catch (e: IOException) {
            println("Error running Python script: ${e.message}")
            e.printStackTrace()
            false
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            println("Python script execution interrupted.")
            false
        }
    }
}




fun createDocxUsingPython1(templatePath: String, outputPath: String, data: AllData): Boolean {
    try {
        // 1. إنشاء مِلَفّ JSON مؤقت
        val jsonFile = File.createTempFile("investigation_data", ".json")
        val jsonString = Json.encodeToString(data)
        jsonFile.writeText(jsonString)

        // 2. مسار سكربت Python
        val pythonScript = "src\\main\\kotlin\\python\\create_report.py" // غيّر هذا حسب موقع سكربتك

        // 3. تجهيز الأمر
        val processBuilder = ProcessBuilder(
            "python", pythonScript, templatePath, outputPath, jsonFile.absolutePath
        )

        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            println("[Python] $line")
        }

        val exitCode = process.waitFor()
        println("Process exited with code $exitCode")

        jsonFile.delete() // تنظيف الملف المؤقت
        return exitCode == 0
    } catch (e: Exception) {
        println("❌ Error running Python script: ${e.message}")
        return false
    }
}
