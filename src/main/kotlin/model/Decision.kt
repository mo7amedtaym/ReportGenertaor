package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import java.util.*

data class Decision (
    val D: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val id: String = UUID.randomUUID().toString(),
){
    @Override
    override fun toString(): String {
        return D.value.text
    }
}