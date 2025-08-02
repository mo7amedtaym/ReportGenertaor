package view

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import customMatrials.DecisionCard
import customMatrials.ErrorSnackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Decision
import org.albarmajy.traffic.customMatrials.CustomButton
import viewModel.QuestionsViewModel
import viewModel.VehicleViewModel

@Composable
fun DecisionView(viewModel: QuestionsViewModel, v:VehicleViewModel, onNextClicked: () -> Unit, onBackClicked: () -> Unit) {
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val scaffoldState = rememberScaffoldState()
    var showDialog by remember { mutableStateOf(false) }
    var defaultDecisions = listOf(
        Decision(mutableStateOf(TextFieldValue("يخلي سبيل المتهم/ ${v.name.text} – من ديوان القسم ما لم يكن مطلوباً محبوساً لسبب أخر وذلك إذا سدد ضماناً مالياً الف جنية والا يُعرض علينا للنظر في أمره."))),
        Decision(mutableStateOf(TextFieldValue("يُكلف احد السادة المهندسين الفنيين بوحدة المرور برفع بصمتي الشاسيه والماتور للمركبة الرقيمة ( ${v.plate.text} ) ال${v.vehicleType} ${v.mror} مع إعداد تقرير مفصل بذلك يعرض علينا فور الانتهاء منه."))),
        Decision(mutableStateOf(TextFieldValue("تسلم ال${v.vehicleType} لمالكها بالإيصال الدال على ذلك ما لم يكن لدى جهه المرور مانع عقب تنفيذ البند السابق."))),
        Decision(mutableStateOf(TextFieldValue("تسلم رخصتي القيادة والتسيير لمالكها"))),
        Decision(mutableStateOf(TextFieldValue("تدرج التحقيقات على منظومة نيابة المرور"))),
        Decision(mutableStateOf(TextFieldValue("تعرض الأوراق للتصرف")))

    )
    val labels = listOf(
        "أولا",
        "ثانياً",
        "ثالثاً",
        "رابعاً",
        "خامساً",
        "سادساً",
        "سابعاً",
        "ثامناً",
        "تاسعاً",
        "عاشراً"
    )
    viewModel.addDecisions(defaultDecisions)


    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false }, // إغلاق النافذة عند النقر خارجها
            title = {
                Text(
                    "إعادة تهيئة الأسئلة والإجابات",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xffF81973)
                )
            }, // عنوان النافذة
            text = { Text("سيتم حذف جميع التغييرات\nمثل اي تعديل تم داخل هذه الصفحة مثل إضافة سؤال او حذف سؤال او إدراج إجابة.\nاضغط موافق لإعادة تهيئة صفحة الأسئلة من جديد!  ") }, // الرسالة
            confirmButton = {
                TextButton(
                    onClick = {
//                        viewModel.resetQuestions(defaultQuestions)
                        showDialog = false
                        // كود الحذف هنا
                    }
                ) {
                    Text("موافق", fontWeight = FontWeight.Bold, color = Color(0xffF81973))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("لا", color = Color.Black)
                }
            }
        )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            ErrorSnackbar(
                show = viewModel.showErrorSnackbar,
                message = viewModel.snackbarMessage,
                onDismiss = { viewModel.dismissSnackbar() }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                "قرار النيابة",
                fontSize = 28.sp,
                modifier = Modifier.padding(12.dp).weight(1f),
                fontWeight = FontWeight.Black,

            )


                LazyColumn(modifier = Modifier.weight(6f)) {
                    itemsIndexed(viewModel.decisionList, key = { index, item -> item.hashCode() }) { index, item ->
                        val isVisible = remember { mutableStateOf(true) }
                        val coroutineScope = rememberCoroutineScope()
                        AnimatedVisibility(
                            visible = isVisible.value,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            DecisionCard(
                                value = item.D.value.text,
                                onValueChanged = { item.D.value = TextFieldValue(it) },
                                onDelete = {
                                    isVisible.value = false
                                    coroutineScope.launch {
                                        delay(300)
                                        viewModel.removeItemDecision(index)

                                    }
                                },
                                onAdd = { viewModel.addItemDecision(index + 1) },
                                label = labels[index],
                            )

                        }

                    }
                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceAround
                            ) {
                                Button(
                                    modifier = Modifier.padding(12.dp),
                                    onClick = { viewModel.addItemDecision() },
                                    enabled = if (viewModel.decisionList.size < labels.size) true else false,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(Color(0xFF005C83), Color.White)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "إضافة")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("إضافة بند جديد")
                                }
                                if (viewModel.decisionList.size >= labels.size) {
                                    Text(
                                        "لقد وصلت الي الحد الأقصى من بنود قرار النيابة.",
                                        fontSize = 12.sp,
                                        color = Color(0xffF81973),

                                        )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(32.dp))
                    }

                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier.weight(1f).padding(top = 24.dp)
                ) {
                    CustomButton(
                        text = "التالي",
                        onClick = {
                            if (viewModel.validateAll()) {
                                onNextClicked()
                            } else {
                                viewModel.showError("يوجد أخطاء في البيانات، الرجاء مراجعتها")
                            }
                        }
                    )
                    CustomButton(
                        text = "عودة",
                        backColor = Color(0xFFF2F2F2),
                        textColor = Color(0xFF0D415B),
                        onClick = {
                            onBackClicked()
                        }
                    )

                }
        }

    }
}


