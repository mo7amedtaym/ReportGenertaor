package viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import model.QandA
import model.TrafficUnit
import model.VehicleType
import java.time.LocalDate
import java.time.Period

class VehicleViewModel {

    // accused data
    var name by mutableStateOf(TextFieldValue(""))
    var job by mutableStateOf(TextFieldValue(""))
    var address by mutableStateOf(TextFieldValue(""))
    var nationalId by mutableStateOf(TextFieldValue(""))

    // vehicle data
    var vehicleType by mutableStateOf<VehicleType?>(VehicleType.CAR)
    var plate by mutableStateOf(TextFieldValue(""))
    var mror by mutableStateOf<TrafficUnit?>(null)
    var mrorText by mutableStateOf(TextFieldValue(""))
    var owner by mutableStateOf(TextFieldValue(""))
    var isOwner by mutableStateOf(false)




    var questions by mutableStateOf<List<QandA>>(emptyList())

    // Errors
    var errors by mutableStateOf<Map<String, String>>(emptyMap())




    var showErrorSnackbar by mutableStateOf(false)
    var snackbarMessage by mutableStateOf("")

    fun showError(message: String) {
        snackbarMessage = message
        showErrorSnackbar = true
    }

    fun dismissSnackbar() {
        showErrorSnackbar = false
    }



    fun validateAll(): Boolean {
        val newErrors = mutableMapOf<String, String>()

        if (name.text.isBlank()) newErrors["name"] = "يجب إدخال اسم المتهم"
        if (job.text.isBlank()) newErrors["job"] = "يجب إدخال وظيفة المتهم"
        if (address.text.isBlank()) newErrors["address"] = "يجب إدخال عنوان المتهم"
        if (plate.text.isBlank()) newErrors["plate"] = "يجب إدخال بيانات لوحة المركبة"
        if (owner.text.isBlank()) newErrors["owner"] = "يجب إدخال اسم مالك المركبة"
        if (mrorText.text.isBlank()) newErrors["mror"] = "يجب اختيار مرور المركبة"
        if(!validateId().isValid) newErrors["nationalId"] = validateId().errorMessage!!

        errors = newErrors
        return newErrors.isEmpty()
    }

    private fun validId(text: TextFieldValue): Boolean {
        return text.text.length == 14 && text.text.all { it.isDigit() }
    }



    fun getAge():Int?{
        try {
            // تأكد أن الرَّقَم القومي يحتوي على 14 رقمًا
            if (nationalId.text.length < 7) return null

            // تحديد السنة حسب أول رَقَم
            val century = when (nationalId.text[0]) {
                '2' -> "19"
                '3' -> "20"
                else -> return null
            }

            val year = (century + nationalId.text.substring(1, 3)).toInt()
            val month = nationalId.text.substring(3, 5).toInt()
            val day = nationalId.text.substring(5, 7).toInt()

            val birthDate = LocalDate.of(year, month, day)
            val today = LocalDate.now()

            val age = Period.between(birthDate, today).years
            return age

        } catch (e: Exception) {
            return null // في حالة وجود خطأ في البنية
        }
    }
    fun validateId(): ValidationResult {
        return when {
            nationalId.text.isEmpty() -> ValidationResult(
                isValid = false,
                errorMessage = "الحقل لا يمكن أن يكون فارغاً"
            )
            nationalId.text.length != 14 -> ValidationResult(
                isValid = false,
                errorMessage = "يجب أن يتكون الرقم من 14 رقمًا"
            )
            !nationalId.text.all { it.isDigit() } -> ValidationResult(
                isValid = false,
                errorMessage = "يجب أن يحتوي على أرقام فقط"
            )
            (nationalId.text[0] != '2') && (nationalId.text[0] != '3') -> ValidationResult(
                isValid = false,
                errorMessage = "يجب أن يبدأ الرقم القومي بـ '2' او '3'"
            )
            (nationalId.text.substring(1,3) > (LocalDate.now().year-1).toString().substring(2,4) && nationalId.text[0] == '3') || (nationalId.text.substring(3,5) == "00"  || nationalId.text.substring(3,5) > "12") || (nationalId.text.substring(5,7) == "00" || nationalId.text.substring(3,5) > "31") -> ValidationResult(
                isValid = false,
                errorMessage = "تنسيق خاطئ للرقم القومي"
            )

            else -> ValidationResult(
                isValid = true,
                errorMessage = "لا يوجد اخطاء"
            )
        }
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String?
    )

}