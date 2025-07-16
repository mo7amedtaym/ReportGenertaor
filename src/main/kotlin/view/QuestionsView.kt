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
import customMatrials.ErrorSnackbar
import customMatrials.QuestionCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.QandA
import org.albarmajy.traffic.customMatrials.CustomButton
import viewModel.QuestionsViewModel
import viewModel.ReportViewModel
import viewModel.VehicleViewModel


@Composable
fun QuestionsView(viewModel: QuestionsViewModel, v:VehicleViewModel, r: ReportViewModel, onNextClicked: () -> Unit, onBackClicked: () -> Unit) {
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val scaffoldState = rememberScaffoldState()
    val qAndAList = viewModel.qAndAList
    var showDialog by remember { mutableStateOf(false) }
    var defaultQuestions = listOf(
        QandA(mutableStateOf(TextFieldValue("ما قولك فيما هو منسوب إليك من انك متهم بقياده ال${v.vehicleType} رقم ( ${v.plate.text} ) عكس الاتجاه في الطريق العام على النحو المبين بالاوراق ؟"))),
        QandA(mutableStateOf(TextFieldValue("ما تفصيلات اعترافك؟"))),
        QandA(mutableStateOf(TextFieldValue("متى وأين حدث ذلك ؟"))),
        QandA(mutableStateOf(TextFieldValue("من كان برفقتك آنذاك؟"))),
        QandA(mutableStateOf(TextFieldValue("ما سبب ومناسبة تواجدك بالمكان والزمان آنفى الذكر؟"))),
        QandA(mutableStateOf(TextFieldValue("ما صلتك بال${v.vehicleType} ( ${v.plate.text} ) ؟"))),
        QandA(mutableStateOf(TextFieldValue("هل تحمل رخصة قيادة خاصة بك ؟"))),
        QandA(mutableStateOf(TextFieldValue("ما السبب الذي دعاك للسير عكس الاتجاه ؟"))),
        QandA(mutableStateOf(TextFieldValue("هل قمت بالسير عكس الاتجاه من قبل؟"))),
        QandA(mutableStateOf(TextFieldValue("صف لنا الطريق محل الواقعة؟"))),
        QandA(mutableStateOf(TextFieldValue("ما هى ظروف ضبطك وعرضك علينا؟"))),
        QandA(mutableStateOf(TextFieldValue("متى واين حدث ذلك؟"))),
        QandA(mutableStateOf(TextFieldValue("هل هناك ثمة تفتيش انصرف لشخصك؟"))),
        QandA(mutableStateOf(TextFieldValue("ما علاقتك بال${r.officerRank} ${r.officerName.text} وهل يوجد خلافات سابقة؟"))),
        QandA(mutableStateOf(TextFieldValue("وما قولك فيما سطره الضابط سالف الذكر بالمحضر المؤرخ ${r.reportDate.text} الساعة ${r.formatToArabicTime(r.reportTime.text)} \"تلوناه عليه تفصيلا\" ؟"))),
        QandA(mutableStateOf(TextFieldValue("هل لديك ثمة سوابق جنائية ؟"))),
        QandA(mutableStateOf(TextFieldValue("هل لديك اسم شهرة ؟"))),
        QandA(mutableStateOf(TextFieldValue("أنت متهم بقياده ال${v.vehicleType} رقم ( ${v.plate.text} ) عكس الاتجاه في الطريق العام على النحو المبين بالاوراق ؟"))),
        QandA(mutableStateOf(TextFieldValue("هل لديك أقوال أخرى؟")), mutableStateOf(TextFieldValue("لا تمت أقواله ووقع منه بذلك /")))
    )

    viewModel.addQuestions(defaultQuestions)

    if (showDialog){
        AlertDialog(
            onDismissRequest = { showDialog = false }, // إغلاق النافذة عند النقر خارجها
            title = { Text("إعادة تهيئة الأسئلة والإجابات", fontWeight = FontWeight.Bold, color = Color(0xffF81973)) }, // عنوان النافذة
            text = { Text("سيتم حذف جميع التغييرات\nمثل اي تعديل تم داخل هذه الصفحة مثل إضافة سؤال او حذف سؤال او إدراج إجابة.\nاضغط موافق لإعادة تهيئة صفحة الأسئلة من جديد!  ") }, // الرسالة
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetQuestions(defaultQuestions)
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
                "أسئلة النيابة وإجابات المتهم",
                fontSize = 28.sp,
                modifier = Modifier.padding(12.dp).weight(1f),
                fontWeight = FontWeight.Black,

            )
            LazyColumn(modifier = Modifier.weight(6f)) {
                itemsIndexed(viewModel.qAndAList, key = { index, item -> item.hashCode() }) { index, item ->
                    val isVisible = remember { mutableStateOf(true) }
                    val coroutineScope = rememberCoroutineScope()
                    AnimatedVisibility(
                        visible = isVisible.value,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        QuestionCard(
                            qValue = item.Q.value.text,
                            aValue = item.A.value.text,
                            onQChanged = { item.Q.value = TextFieldValue(it) },
                            onAChanged = { item.A.value = TextFieldValue(it) },
                            onDelete = {
                                isVisible.value = false
                                coroutineScope.launch {
                                    delay(300)
                                    viewModel.removeItem(index)
//                                    try {
//                                        defaultQuestions = defaultQuestions - defaultQuestions[index]
//                                    }catch (e: Exception){
//                                        println(e.message)
//                                    }
                                }
                            },
                            onAdd = { viewModel.addItem(index + 1) },
                        )

                    }

                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.padding(12.dp),
                            onClick = { viewModel.addItem() },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(Color(0xFF005C83), Color.White)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "إضافة")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("إضافة سؤال جديد")
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }

            }
            Row(horizontalArrangement = Arrangement.spacedBy(18.dp), modifier = Modifier.weight(1f).padding(top = 24.dp)) {
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
                CustomButton(
                    text = "تهيئة الأسئلة من جديد",
                    backColor = Color.White,
                    textColor = Color(0xFF0D415B),
                    onClick = {

                        showDialog= !showDialog

                    }
                )
            }
        }

    }
}
