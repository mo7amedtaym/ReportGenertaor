import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.platform.Typeface

private val Cairo = FontFamily(
    Font(resource = "fonts/Cairo-Regular.ttf")
)

private val AppTypography = Typography(
    defaultFontFamily = Cairo
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = AppTypography,
        content = content
    )
}
