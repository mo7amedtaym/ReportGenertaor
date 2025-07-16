package model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import java.util.*

data class QandA (
    val Q: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val A: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    val id: String = UUID.randomUUID().toString(),
)
