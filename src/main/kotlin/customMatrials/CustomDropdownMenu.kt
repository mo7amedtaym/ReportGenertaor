package customMatrials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import utiity.normalizeArabic

@Composable
fun <T> CustomDropdownMenu(
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    items: List<T>,
    label: String,
    modifier: Modifier = Modifier,
    mainColor: Color = Color(0xFF005C83),
    textAlign: TextAlign = TextAlign.Right,
    errorColor: Color= Color(0xffF81973),
    isError: Boolean = false

) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }

    var highlightedIndex by remember { mutableStateOf(-1) }
    val density = LocalDensity.current

    Box(modifier = modifier.onPreviewKeyEvent { event ->
        if (!expanded) return@onPreviewKeyEvent false

        when (event.type) {
            KeyEventType.KeyDown -> {
                when (event.key) {
                    Key.DirectionDown -> {
                        expanded =true
                        highlightedIndex = (highlightedIndex + 1).coerceAtMost(items.lastIndex)
                        true
                    }
                    Key.DirectionUp -> {
                        expanded =true
                        highlightedIndex = (highlightedIndex - 1).coerceAtLeast(0)
                        true
                    }
                    Key.Enter -> {
                        if (highlightedIndex in items.indices) {
                            onItemSelected(items[highlightedIndex])
                            expanded = false
                        }
                        true
                    }
                    Key.Escape -> {
                        expanded = false
                        true
                    }
                    else -> false
                }
            }

            else -> false
        }
    }) {
        OutlinedTextField(
            value = selectedItem?.toString() ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(label, fontSize = 14.sp, color = mainColor)
            },
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                IconButton(onClick = {
                    expanded = !expanded
                    highlightedIndex = if (selectedItem != null) {
                        items.indexOf(selectedItem)
                    } else -1
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "اختر",
                        tint = mainColor
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = mainColor,
                unfocusedBorderColor = mainColor,
                cursorColor = mainColor,
                disabledTextColor = Color.Black,
                errorBorderColor = errorColor,
                errorCursorColor = errorColor
            ),
            textStyle = TextStyle(
                color = mainColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = textAlign
            ),
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            isError = isError

        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(with(density) { textFieldSize.width.toDp() })
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    modifier = Modifier.background(
                        if (index == highlightedIndex) Color.LightGray.copy(alpha = 0.3f) else Color.Transparent
                    )
                ) {
                    Text(
                        item.toString(),
                        textAlign = textAlign,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun <T> CustomDropdownMenuWithInput(
    inputText: String,
    onInputChange: (String) -> Unit,
    onItemSelected: (T) -> Unit,
    items: List<T>,
    label: String,
    modifier: Modifier = Modifier,
    mainColor: Color = Color(0xFF005C83),
    textAlign: TextAlign = TextAlign.Right,
    errorColor: Color = Color(0xffF81973),
    isError: Boolean = false,
    itemMapper: (T) -> String = { it.toString() }, // تحويل العنصر لنص
    onCustomValueEntered: ((String) -> T)? = null // إدخال قيمة غير موجودة
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var highlightedIndex by remember { mutableStateOf(-1) }
    val density = LocalDensity.current
    var filteredList by remember { mutableStateOf(items.filter {
        it.toString().contains(inputText)
    }) }
    filteredList = items.filter {
        normalizeArabic(it.toString()).contains(inputText, ignoreCase = true)
    }

    Box(modifier = modifier.onPreviewKeyEvent { event ->
        if (!expanded) return@onPreviewKeyEvent false
        when (event.type) {
            KeyEventType.KeyDown -> {
                when (event.key) {
                    Key.DirectionDown -> {
                        expanded = true
                        highlightedIndex = (highlightedIndex + 1).coerceAtMost(items.lastIndex)
                        true
                    }
                    Key.DirectionUp -> {
                        expanded = true
                        highlightedIndex = (highlightedIndex - 1).coerceAtLeast(0)
                        true
                    }
                    Key.Enter -> {
                        if (highlightedIndex in items.indices) {
                            onItemSelected(items[highlightedIndex])
                            onInputChange(itemMapper(items[highlightedIndex]))
                            expanded = false
                        } else if (onCustomValueEntered != null) {
                            onItemSelected(onCustomValueEntered(inputText))
                            expanded = false
                        }
                        true
                    }
                    Key.Escape -> {
                        expanded = false
                        true
                    }
                    else -> false
                }
            }

            else -> false
        }
    }) {
        OutlinedTextField(
            value = inputText,
            onValueChange = {
//                filteredList = items.

                onInputChange(it)
                expanded = true

            },
            label = {
                Text(label, fontSize = 14.sp, color = mainColor)
            },
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "اختر",
                        tint = mainColor
                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = mainColor,
                unfocusedBorderColor = mainColor,
                cursorColor = mainColor,
                errorBorderColor = errorColor,
                errorCursorColor = errorColor
            ),
            textStyle = TextStyle(
                color = mainColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = textAlign
            ),
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size
                }
                .fillMaxWidth()
                .padding(4.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        expanded = true
                    }
                    else
                        expanded = false
                }
            ,
            singleLine = true,
            isError = isError
        )

        var listHeight by remember {
            mutableStateOf(
                50
            )
        }
        listHeight=when(filteredList.size){
            0->-10
            1->-10
            2->50
            3->100
            4->100
            else -> 160
        }

//            if (filteredList.size>4){
//                        160
//                    }
//                    else{
//                        filteredList.size*30
//                    }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(false,true,true),
            offset = DpOffset(0.dp, 0.dp),
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .height(with(LocalDensity.current) { (textFieldSize.height+listHeight).toDp() })

           // offset = DpOffset(x = 0.dp, y = with(density) { textFieldSize.height.toDp() }) // ← هذا السطر المهم
        ) {
            if (filteredList.isEmpty()) {
                DropdownMenuItem(onClick = {}, enabled = false) {
                    Text(
                        "غير متطابق مع أي عنصر",
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = textAlign,
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                filteredList.forEachIndexed { index, item ->
                    val itemText = itemMapper(item)
                    DropdownMenuItem(
                        onClick = {
                            onInputChange(itemText)
                            onItemSelected(item)
                            expanded = false
                        },
                        modifier = Modifier.background(
                            if (index == highlightedIndex) Color.LightGray.copy(alpha = 0.3f) else Color.Transparent
                        )
                    ) {
                        Text(
                            itemText,
                            textAlign = textAlign,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold
                        )
                    }


                }
            }
        }

    }
}

