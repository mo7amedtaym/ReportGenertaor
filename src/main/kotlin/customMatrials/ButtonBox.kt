package customMatrials

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ButtonBox(
    image: Painter,
    description: String = "action button",
    size: Dp = 60.dp,
    imageModifier: Modifier= Modifier,
    onClick: () -> Unit
){
    Box(
        modifier = Modifier.border(
            width = 2.dp,
            color = Color(0xFF0D415B),
            shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .size(size)
            .clickable { onClick() }
    ){
        Image(
            painter = image,
            contentDescription = description,
            modifier = imageModifier
        )
    }
}