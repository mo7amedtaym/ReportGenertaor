package utiity

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

fun chooseSaveFilePath(
    title: String = "اختر مكان حفظ الملف",
    defaultFileName: String = "file.txt",
    allowedExtensions: List<String> = listOf("txt")
): File? {
    val fileChooser = JFileChooser().apply {
        dialogTitle = title
        selectedFile = File(defaultFileName)
        fileFilter = FileNameExtensionFilter(
            "${allowedExtensions.joinToString(", ")} files",
            *allowedExtensions.toTypedArray()
        )
    }

    val userSelection = fileChooser.showSaveDialog(null)

    return if (userSelection == JFileChooser.APPROVE_OPTION) {
        var file = fileChooser.selectedFile
        val extension = allowedExtensions.firstOrNull()
        if (extension != null && !file.name.endsWith(".$extension")) {
            file = File(file.absolutePath + ".$extension")
        }
        file
    } else {
        null
    }
}
