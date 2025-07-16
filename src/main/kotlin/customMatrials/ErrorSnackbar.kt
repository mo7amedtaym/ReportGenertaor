package customMatrials

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ErrorSnackbar0(
    show: Boolean,
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    backColor: Color = Color(0xffF81973)
) {
    if (show) {
        Snackbar(
            modifier = modifier.padding(16.dp).width(250.dp),
            action = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("حسناً")
                }
            },
            backgroundColor = backColor.copy(alpha =1f)
        ) {
            Text(
                text = message,
                color = Color.White,
                textAlign = TextAlign.Right
            )
        }
    }
}

@Composable
fun ErrorSnackbar(
    show: Boolean,
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    backColor: Color = Color(0xffF81973)

) {
    // حالة التحكم بمدة ظهور الـ Snackbar
    val showState = remember { mutableStateOf(show) }

    LaunchedEffect(show) {
        if (show) {
            showState.value = true
            delay(5000) // 5 ثوانٍ
            showState.value = false
            onDismiss()
        }
    }

    AnimatedVisibility(
        visible = showState.value,
        enter = fadeIn() + slideInVertically { it },
        exit = fadeOut() + slideOutVertically { it },
        modifier = modifier.wrapContentWidth()
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentWidth()
                .shadow(4.dp, RoundedCornerShape(8.dp))
                ,
            color = backColor.copy(alpha = 0.9f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(1f, fill = false)
                )

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(
                    onClick = {
                        showState.value = false
                        onDismiss()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("حسناً")
                }
            }
        }
    }
}