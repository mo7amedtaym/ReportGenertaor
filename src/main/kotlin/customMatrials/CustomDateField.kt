package customMatrials

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CustomDateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    upEvent: () -> Unit,
    downEvent: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    mainColor: Color = Color(0xFF005C83),
    textAlign: TextAlign = TextAlign.Right,
    errorColor: Color= Color(0xffF81973),
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
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            LocalDate.parse(value, formatter)
            true
        } catch (_: Exception) {
            false
        }
    }

    Column(modifier = modifier.padding(4.dp)) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val digits = newValue.text.filter { it.isDigit() }.take(8)
                val formatted = buildString {
                    when (digits.length) {
                        in 0..4 -> append(digits)
                        5 -> {
                            append(digits.substring(0, 4))
                            append("/")
                            if (digits[4]>'1')
                                append("0${digits.substring(4)}")
                            else
                                append(digits.substring(4))
                        }
                        6 -> {
                            append(digits.substring(0, 4))
                            append("/")
                            append(digits.substring(4))
                        }
                        7 -> {
                            append(digits.substring(0, 4))
                            append("/")
                            append(digits.substring(4, 6))
                            append("/")
                            if (digits[6]>'1')
                                append("0${digits.substring(6)}")
                            else
                                append(digits.substring(6))
                        }
                        else -> {
                            append(digits.substring(0, 4))
                            append("/")
                            append(digits.substring(4, 6))
                            append("/")
                            append(digits.substring(6))
                        }
                    }
                }

                // حساب موضع المؤشر الجديد
                val newCursorPos = when {
                    newValue.selection.start == 4 && formatted.length > 4 -> 5
                    newValue.selection.start == 7 && formatted.length > 7 -> 8
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
                errorBorderColor = errorColor,
                errorCursorColor = errorColor
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
                .onPreviewKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionUp && isValid) {
                        upEvent()
                        true
                    } else if (keyEvent.type == KeyEventType.KeyDown && keyEvent.key == Key.DirectionDown && isValid) {
                        downEvent()
                        true
                    }
                    else{
                        false
                    }
                },


        )

        if ((!isValid && value.isNotEmpty() &&!isFocused) || isError) {
            Text(
                text = "صيغة التاريخ غير صحيحة (مثال: 2023/12/31)",
                color = errorColor,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}

