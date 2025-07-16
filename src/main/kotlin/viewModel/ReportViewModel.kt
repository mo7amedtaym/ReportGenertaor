package viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import model.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReportViewModel {
    // Police Report Data
    var caseNumber by mutableStateOf(TextFieldValue(""))
    var officerName by mutableStateOf(TextFieldValue(""))
    var officerRank by mutableStateOf<PoliceRank?>(null)
    var policeDepartment by mutableStateOf<PoliceDepartment?>(null)
    var reportDate by mutableStateOf(TextFieldValue(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))))
    var reportTime by mutableStateOf(TextFieldValue(""))

    // Prosecution Report Data
    var prosecutor by mutableStateOf(TextFieldValue(""))
    var secretary by mutableStateOf(TextFieldValue(""))
    var prosecutionDate by mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
    var prosecutionTime by mutableStateOf(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm")))



    var questions by mutableStateOf<List<QandA>>(emptyList())

    // Errors
    var errors by mutableStateOf<Map<String, String>>(emptyMap())



    // Computed Properties
    val policeReport: PoliceReport?
        get() = if (validatePoliceReport()) {
            PoliceReport(
                reportNumber = caseNumber.text,
                officerName = officerName.text,
                officerRank = officerRank!!,
                department = policeDepartment!!,
                reportDate = reportDate.text,
                reportTime = reportTime.text
            )
        } else null

    val prosecutionReport: ProsecutionReport?
        get() = if (validateProsecutionReport()) {
            ProsecutionReport(
                prosecutorName = prosecutor.text,
                secretaryName = secretary.text,
                prosecutionDate = prosecutionDate,
                prosecutionTime= prosecutionTime
            )
        } else null

    fun formatToArabicTime(timeString: String): String {
        if (timeString.isBlank()) return "00:00"

        return try {
            val time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))
            val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
            val formatted = time.format(formatter)
            formatted.replace("AM", "صباحًا").replace("PM", "مساءً")
        } catch (e: Exception) {
            "تنسيق غير صحيح"
        }
    }


    var showErrorSnackbar by mutableStateOf(false)
    var snackbarMessage by mutableStateOf("")

    fun showError(message: String) {
        snackbarMessage = message
        showErrorSnackbar = true
    }

    fun dismissSnackbar() {
        showErrorSnackbar = false
    }

    // Validation Functions
    fun validatePoliceReport(): Boolean {
        return caseNumber.text.isNotBlank() &&
                officerName.text.isNotBlank() &&
                officerRank != null &&
                policeDepartment != null &&
                reportDate.text.isNotBlank() &&
                reportTime.text.isNotBlank()
    }

    fun validateProsecutionReport(): Boolean {
        return prosecutor.text.isNotBlank() &&
                secretary.text.isNotBlank() &&
                prosecutionDate.isNotBlank() &&
                prosecutionTime.isNotBlank()
    }

    fun validateAll(): Boolean {
        val newErrors = mutableMapOf<String, String>()

        if (caseNumber.text.isBlank()) newErrors["caseNumber"] = "يجب إدخال رقم القضية"
        if (officerName.text.isBlank()) newErrors["officerName"] = "يجب إدخال اسم الضابط"
        if (officerRank == null) newErrors["officerRank"] = "يجب اختيار الرتبة"
        if(policeDepartment == null) newErrors["policeDepartment"] = "يجب اختيار قسم الشرطة"
        if(!isDateValid(reportDate.text)) newErrors["reportDate"] = "يجب كتابة تاريخ محضر القسم"
        if(!isTimeValid(reportTime.text)) newErrors["reportTime"] = "يجب كتابة وقت محضر القسم"


        if (prosecutor.text.isBlank())  newErrors["prosecutor"] = "يجب إدخال اسم وكيل النيابة"
        if (secretary.text.isBlank())  newErrors["secretary"] = "يجب إدخال اسم سكرتير التحقيق"
        if (!isDateValid(prosecutionDate))  newErrors["prosecutor"] = "يجب إدخال اسم وكيل النيابة"
        if (!isTimeValid(prosecutionTime))  newErrors["prosecutor"] = "يجب إدخال اسم وكيل النيابة"

        errors = newErrors
        return newErrors.isEmpty()
    }

//    // Questions Management
//    fun addQuestion(question: String, answer: String = "") {
//        questions = questions + QandA(mutableStateOf(question), (answer))
//    }
//
//    fun removeQuestion(index: Int) {
//        if (index in questions.indices) {
//            questions = questions.toMutableList().apply { removeAt(index) }
//        }
//    }
//
//    fun updateQuestion(index: Int, newQuestion: String, newAnswer: String) {
//        if (index in questions.indices) {
//            questions = questions.toMutableList().apply {
//                this[index] = QandA(newQuestion, newAnswer)
//            }
//        }
//    }
//
//    // Data Submission
//    fun submitReport() {
//        if (!validateAll()) {
//            throw IllegalStateException("All required fields must be filled")
//        }
//
//        // Here you would typically send data to backend
//        // or save to database
//    }

    fun isDateValid(value: String): Boolean{
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                LocalDate.parse(value, formatter)
                return true
            } catch (_: Exception) {
                return false
            }
        }

    fun isTimeValid(value: String): Boolean{
        return when {
            value.isEmpty() -> false
            value.length != 5 -> false
            else -> try {
                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                LocalTime.parse(value, formatter)
                true
            } catch (_: Exception) {
                false
            }
        }

    }

}