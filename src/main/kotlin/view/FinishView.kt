package view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import customMatrials.ButtonBox

@Composable
fun FinishView(onSave: () -> Unit, onPrint: () -> Unit, onWord: () -> Unit,){

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "عمل رائع",
            fontWeight = FontWeight.Black,
            fontSize = 68.sp,
            color = Color(0xFF0D415B)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "لقد أتممت تحقيقات النيابة بنجاح",
            fontSize = 18.sp,
            color = Color(0xFF0D415B)
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ButtonBox(
                painterResource("image/print.png"),
                "print the report",
                imageModifier = Modifier.padding(14.dp)

            ){
                onPrint()
            }
            ButtonBox(
                painterResource("image/save.png"),
                "print the report",
                imageModifier = Modifier.padding(10.dp)
            ){
                onSave()
            }
            ButtonBox(
                painterResource("image/word.png"),
                "print the report",
                imageModifier = Modifier.padding(14.dp)
            ){
                onWord()
            }
        }
    }
}