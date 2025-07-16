package customMatrials

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String?,
    modifier: Modifier = Modifier,
    mainColor: Color = Color(0xFF005C83),
    textAlign: TextAlign = TextAlign.Right,
    numbersOnly: Boolean = false,
    isEnabled: Boolean = true,
    errorColor: Color= Color(0xffF81973),
    isError: Boolean = false,
    placeholder: String= "إجابة المتهم هنا",
    errorMessage: String = "تأكد من صحة البيانات!",
) {

    OutlinedTextField(
        enabled = isEnabled,
        value = value,
        onValueChange = { newValue ->
            if (!numbersOnly || newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        placeholder = { Text(placeholder, color = mainColor.copy(alpha = 0.5f)) },
        label = {
            if (label != null){
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = mainColor,
                )
            }
        },

        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = mainColor,
            unfocusedBorderColor = mainColor,
            cursorColor = mainColor,
            textColor = mainColor,
            errorBorderColor = errorColor,
            errorCursorColor = errorColor,
            disabledBorderColor = Color(0xDADADA)
        ),
        textStyle = TextStyle(
            color = mainColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = textAlign,
            textDirection = TextDirection.Rtl // هذا السطر يحل المشكلة
        ),
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = if (numbersOnly) KeyboardType.Number else KeyboardType.Text
        ),
        modifier = modifier
            .padding(4.dp)
            .semantics {
                testTag = "customTextField"
            },
        isError = isError,

    )

}