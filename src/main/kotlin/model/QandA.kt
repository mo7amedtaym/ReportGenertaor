package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.serialization.Serializable
import java.util.*

data class QandA (
    val Q: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val A: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val id: String = UUID.randomUUID().toString(),
){
    fun toQandAData(): QandAData {
        return QandAData(Q.value.text, A.value.text)
    }


    companion object {
        // دالة مساعدة لإنشاء QandA من QandAData
        fun fromQandAData(data: QandAData): QandA {
            return QandA(mutableStateOf(TextFieldValue(data.Q)), mutableStateOf(TextFieldValue(data.A)))
        }


    }
}

@Serializable // ضع هذا التعليق التوضيحي
data class QandAData(
    val Q: String, // String بدلاً من MutableState<TextFieldValue>
    val A: String, // String بدلاً من MutableState<TextFieldValue>
)

