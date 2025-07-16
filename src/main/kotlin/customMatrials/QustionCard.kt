package customMatrials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuestionCard(
    modifier: Modifier = Modifier,
    qValue: String,
    aValue: String,
    onQChanged: (String)-> Unit,
    onAChanged: (String)-> Unit,
    onDelete: ()-> Unit,
    onAdd:()-> Unit,
    backColor: Color = Color(0xFFDADADA).copy(0.6f)

    ){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backColor)

    ){
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier= Modifier.weight(9f)) {
                Row {
                    Text("س", fontSize = 36.sp,modifier = Modifier.width(40.dp))
                    QuestionTextField(modifier = Modifier.fillMaxWidth(), placeholder = "وجه سؤال للمتهم", value = qValue, onValueChange = onQChanged, label = null,)
                }
                Row {
                    Text("ج", fontSize = 36.sp,modifier = Modifier.width(40.dp))
                    QuestionTextField(modifier = Modifier.fillMaxWidth(), value = aValue, onValueChange = onAChanged, label = null,)
                }
            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(6.dp).clip(RoundedCornerShape(24.dp)).background(Color.White).padding(6.dp)

            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "حذف")
                }
                IconButton(onClick = onAdd) {
                    Icon(Icons.Default.Add, contentDescription = "اضف سؤال جديد")
                }
            }

        }


    }
}