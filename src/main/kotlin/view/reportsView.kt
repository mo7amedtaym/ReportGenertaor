package org.albarmajy.traffic.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import customMatrials.CustomDateInputField
import customMatrials.CustomDropdownMenu
import customMatrials.CustomTimeInputField
import customMatrials.ErrorSnackbar
import model.PoliceDepartment
import model.PoliceRank
import org.albarmajy.traffic.customMatrials.CustomButton
import org.albarmajy.traffic.customMatrials.CustomTextField
import viewModel.ReportViewModel


@Composable
fun ReportsView(viewModel: ReportViewModel, onNextClicked: () -> Unit) {
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val scaffoldState = rememberScaffoldState()

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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                "محضر سير عكس اتجاه",
                fontSize = 28.sp,
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Black
            )

            Row(modifier = Modifier.padding(12.dp)) {
                // Police Report Section
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xDADADA).copy(0.6f))
                        .padding(12.dp)
                        .weight(0.5f)
                        .onGloballyPositioned { coordinates -> boxSize = coordinates.size },
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("بيانات محضر الشرطة", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.caseNumber,
                            onValueChange = {
                                viewModel.caseNumber = it
                                viewModel.errors = viewModel.errors - "caseNumber"
                            },
                            label = "رقم القضية",
                            numbersOnly = true,
                            isError = viewModel.errors.containsKey("caseNumber")
                        )
                        if (viewModel.caseNumber.text.length > 4) {
                            Text(
                                "عاده ما يكون رقم القضية مكون من 3 او 4 أرقام فقط.!",
                                fontSize = 12.sp,
                                color = Color(0xffF04C8E),
                                modifier = Modifier.fillMaxWidth(),

                                )
                        }

                        Row(modifier = Modifier.wrapContentHeight()) {
                            CustomTextField(
                                modifier = Modifier.weight(2f),
                                value = viewModel.officerName,
                                onValueChange = { viewModel.officerName = it
                                    viewModel.errors = viewModel.errors - "officerName"},
                                label = "اسم الضابط",
                                isError = viewModel.errors.containsKey("officerName")
                            )

                            CustomDropdownMenu(
                                modifier = Modifier.weight(1f),
                                selectedItem = viewModel.officerRank,
                                onItemSelected = { viewModel.officerRank = it
                                    viewModel.errors = viewModel.errors - "officerRank"},
                                items = PoliceRank.entries,
                                label = "الرتبة",
                                isError = viewModel.errors.containsKey("officerRank")
                            )
                        }

                        CustomDropdownMenu(
                            modifier = Modifier.fillMaxWidth(),
                            selectedItem = viewModel.policeDepartment,
                            onItemSelected = { viewModel.policeDepartment = it
                                viewModel.errors = viewModel.errors - "policeDepartment"},
                            items = PoliceDepartment.entries,
                            label = "قسم الشرطة",
                            isError = viewModel.errors.containsKey("policeDepartment")
                        )
                        Row(verticalAlignment = Alignment.Top) {

                            CustomDateInputField(
                                modifier = Modifier.weight(3f),
                                value = viewModel.reportDate.text,
                                onValueChange = { viewModel.reportDate = TextFieldValue(it)
                                    viewModel.errors = viewModel.errors - "reportDate"},
                                upEvent = { viewModel.incrementReportDate() },
                                downEvent = {viewModel.decrementReportDate()},
                                label = "تاريخ المحضر",
                                isError = viewModel.errors.containsKey("reportDate")
                            )

                            Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.CenterHorizontally){
                                CustomTimeInputField(
                                    value = viewModel.reportTime.text,
                                    onValueChange = {
                                        viewModel.reportTime = TextFieldValue(it)
                                        viewModel.errors = viewModel.errors - "reportTime"
                                    },
                                    isError = viewModel.errors.containsKey("reportTime"),
                                    label = "وقت المحضر",

                                    )
                                if (viewModel.isTimeValid(viewModel.reportTime.text)) {
                                    Row{
                                        Text(
                                            viewModel.formatToArabicTime(viewModel.reportTime.text),
                                            fontSize = 12.sp,
                                            color = Color(0xFF0D415B),
                                        )
                                    }
                                }
                            }
                        }


                    }
                }

                // Prosecution Report Section
                Box(
                    modifier = Modifier
                        .height(with(density) { (boxSize.height + 60).toDp() })
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xDADADA).copy(0.6f))
                        .padding(12.dp)
                        .weight(0.5f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("بيانات محضر النيابة", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.prosecutor,
                            onValueChange = { viewModel.prosecutor = it
                                viewModel.errors = viewModel.errors - "prosecutor"},
                            isError = viewModel.errors.containsKey("prosecutor"),
                            label = "وكيل النيابة"
                        )

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.secretary,
                            onValueChange = { viewModel.secretary = it
                                viewModel.errors = viewModel.errors - "secretary"},
                            isError = viewModel.errors.containsKey("secretary"),
                            label = "سكرتير التحقيق"
                        )

                        Row(verticalAlignment = Alignment.Top) {

                            CustomDateInputField(
                                modifier = Modifier.weight(3f),
                                value = viewModel.prosecutionDate,
                                onValueChange = { viewModel.prosecutionDate = it
                                    viewModel.errors = viewModel.errors - "prosecutionDate"},
                                upEvent = { viewModel.incrementDate()},
                                downEvent = {viewModel.decrementDate()},
                                isError = viewModel.errors.containsKey("prosecutionDate"),
                                label = "تاريخ المحضر",
                            )


                            Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.CenterHorizontally){
                                CustomTimeInputField(
                                    value = viewModel.prosecutionTime,
                                    onValueChange = {
                                        viewModel.prosecutionTime = it
                                        viewModel.errors = viewModel.errors - "prosecutionTime"},
                                    isError = viewModel.errors.containsKey("prosecutionTime"),
                                    label = "وقت المحضر",

                                    )
                                if (viewModel.isTimeValid(viewModel.prosecutionTime)) {
                                    Row{
                                        Text(
                                            viewModel.formatToArabicTime(viewModel.prosecutionTime),
                                            fontSize = 12.sp,
                                            color = Color(0xFF0D415B),
                                        )
                                    }
                                }
                            }



                        }
                        Text(
                            "الوقت والتاريخ تم إدراجه تلقائياً وهوا يًمثل الوقت الحالي كما في إعدادات جهاز الكمبيوتر ويمكن تعديلهم بسهولة.",
                            fontSize = 13.sp
                        )
                    }
                }
            }
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
    }


    }
}
