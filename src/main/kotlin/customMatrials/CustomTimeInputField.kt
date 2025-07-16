package customMatrials

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun CustomTimeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    mainColor: Color = Color(0xFF005C83),
    textAlign: TextAlign = TextAlign.Right,
    errorColor: Color = Color(0xffF81973),
    isError: Boolean = false
) {
    var textFieldValue by remember(value) {
        mutableStateOf(TextFieldValue(
            text = value,
            selection = TextRange(value.length)
        ))
    }

    var isFocused by remember { mutableStateOf(false) }

    val isValid = remember(value) {
        try {
            if (value.length != 5) return@remember false
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            LocalTime.parse(value, formatter)
            true
        } catch (_: Exception) {
            false
        }
    }

    Column(modifier = modifier.padding(4.dp)) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val digits = newValue.text.filter { it.isDigit() }.take(4)
                val formatted = buildString {
                    when (digits.length) {
                        0 -> append(digits)
                        1 -> {
                            if (digits[0]>'2')
                                append("0${digits.substring(0)}")
                            else
                                append(digits)
                        }
                        2 -> {
                            append(digits.substring(0, 2))
                        }
                        3 -> {
                            append(digits.substring(0, 2))
                            append(":")
                            if (digits[2]>'5')
                                append("0${digits.substring(2)}")
                            else
                                append(digits.substring(2))
                        }
                        4 -> {
                            append(digits.substring(0, 2))
                            append(":")
                            append(digits.substring(2))
                        }
                    }
                }

                val newCursorPos = when {
                    newValue.selection.start == 2 && formatted.length > 2 -> 3
                    newValue.selection.start == 4 && formatted.length > 4 -> 5
                    else -> newValue.selection.start.coerceIn(0, formatted.length)
                }

                onValueChange(formatted)
                textFieldValue = TextFieldValue(
                    text = formatted,
                    selection = TextRange(newCursorPos)
                )
            },
            label = {
                Text(label, fontSize = 14.sp, color = mainColor)
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            isError = isError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (isValid || value.isEmpty() || isFocused) mainColor else errorColor,
                unfocusedBorderColor = if (isValid || value.isEmpty() || isFocused) mainColor else errorColor,
                cursorColor = mainColor,
                errorCursorColor = errorColor,
                errorBorderColor = errorColor,
            ),
            textStyle = TextStyle(
                color = if (isValid || value.isEmpty() || isFocused) mainColor else errorColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = textAlign
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    if (!focusState.isFocused) {
                        textFieldValue = textFieldValue.copy(
                            selection = TextRange(textFieldValue.text.length)
                        )
                    }
                }
        )

        if ((!isValid && value.isNotEmpty() && !isFocused) || isError) {
            Text(
                text = "صيغة الوقت غير صحيحة (مثال: 09:30)",
                color = errorColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}