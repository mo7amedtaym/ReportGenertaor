package view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import customMatrials.CustomDropdownMenu
import customMatrials.CustomDropdownMenuWithInput
import customMatrials.ErrorSnackbar
import model.TrafficUnit
import model.VehicleType
import org.albarmajy.traffic.customMatrials.CustomButton
import org.albarmajy.traffic.customMatrials.CustomTextField
import viewModel.VehicleViewModel

@Composable
fun VehicleView(viewModel: VehicleViewModel, onNextClicked: () -> Unit, onBackClicked: () -> Unit) {
    val density = LocalDensity.current
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val scaffoldState = rememberScaffoldState()
    var isIdTrue by remember { mutableStateOf(false) }
    val allUnits = remember { mutableStateListOf<TrafficUnit>().apply { addAll(TrafficUnit.defaultUnits) } }
    var selectedText by remember { mutableStateOf("") }

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
                        Text("بيانات المتهم", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.name,
                            onValueChange = {
                                viewModel.name = it
                                viewModel.errors = viewModel.errors - "name"
                            },
                            label = "اسم المتهم",
                            isError = viewModel.errors.containsKey("name")
                        )

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.nationalId,
                            onValueChange = {
                                if (it.text.length<=14){
                                    viewModel.nationalId = it
                                    viewModel.errors = viewModel.errors - "nationalId"
                                }
                                isIdTrue = true
                            },
                            label = "الرقم القومي",
                            numbersOnly = true,
                            isError = viewModel.errors.containsKey("nationalId"),
                            errorMessage = viewModel.validateId().errorMessage!!
                        )
                        if (viewModel.validateId().isValid){
                            Text(
                                "عمر المتهم ${viewModel.getAge()} سنه",
                                fontSize = 12.sp,
                                color = Color(0xFF005C83),
                                modifier = Modifier.fillMaxWidth(),

                            )
                        }
                        if (!(viewModel.validateId().isValid) && viewModel.nationalId.text.length==14){
                            Text(
                                viewModel.validateId().errorMessage!!,
                                fontSize = 12.sp,
                                color = Color(0xffF81973),
                                modifier = Modifier.fillMaxWidth(),

                                )
                        }

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.address,
                            onValueChange = {
                                viewModel.address = it
                                viewModel.errors = viewModel.errors - "address"
                            },
                            label = "عنوان المتهم",
                            isError = viewModel.errors.containsKey("address")
                        )

                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.job,
                            onValueChange = {
                                viewModel.job = it
                                viewModel.errors = viewModel.errors - "job"
                            },
                            label = "الوظيفة",
                            isError = viewModel.errors.containsKey("job")
                        )


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
                        Text("بيانات ال${viewModel.vehicleType}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))

                        Row {

                            Column(Modifier.weight(2f)) {
                                CustomDropdownMenu(
                                    modifier = Modifier.fillMaxWidth(),
                                    selectedItem = viewModel.vehicleType,
                                    onItemSelected = {
                                        viewModel.vehicleType = it
                                        viewModel.errors = viewModel.errors - "vehicleType"
                                    },
                                    items = VehicleType.entries,
                                    label = "نوع المركبة",
                                    isError = viewModel.errors.containsKey("vehicleType")
                                )

                            }


                            Column(Modifier.weight(3f)) {
                                CustomTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = viewModel.plate,
                                    onValueChange = {
                                        viewModel.plate = it
                                        viewModel.errors = viewModel.errors - "plate"
                                    },
                                    isError = viewModel.errors.containsKey("plate"),
                                    label = "لوحة ال${viewModel.vehicleType}"
                                )
                            }
                        }
                        CustomDropdownMenuWithInput(
                            inputText = viewModel.mrorText.text,
                            onInputChange = { viewModel.mrorText = TextFieldValue(it) },
                            onItemSelected = {
                                viewModel.mror = it
                                if (it !in allUnits) allUnits.add(it)
                                viewModel.errors = viewModel.errors - "plate"
                            },
                            isError = viewModel.errors.containsKey("plate"),
                            items = allUnits,
                            label = "وحدة المرور",
                            onCustomValueEntered = { customName ->
                                TrafficUnit.Custom(customName)
                            }
                        )

                        if (viewModel.isOwner){
                            viewModel.owner = viewModel.name
                        }
                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.owner,
                            onValueChange = {
                                viewModel.owner = it
                                viewModel.errors = viewModel.errors - "owner"
                            },
                            isError = viewModel.errors.containsKey("owner"),
                            label = "مالك ال${viewModel.vehicleType}",
                            isEnabled = !(viewModel.isOwner)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy ( 8.dp ), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "هل المتهم هوا نفسه مالك ال${viewModel.vehicleType}؟",
                                fontSize = 13.sp
                            )
                            Checkbox(
                                checked = viewModel.isOwner,
                                onCheckedChange = {viewModel.isOwner = it},
                                colors = CheckboxDefaults.colors(
                                    Color(0xFF0D415B),
                                    Color(0xFF0D415B),
                                    Color(0xFFFFFFFF),

                                )
                            )
                        }



                    }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                CustomButton(
                    text = "التالي",
                    onClick = {
                        if (viewModel.validateAll()) {
                            if (viewModel.mror == null){
                                viewModel.mror = TrafficUnit.Custom(viewModel.mrorText.text)
                            }
                            onNextClicked()
                        } else {
                            viewModel.showError(viewModel.validateId().errorMessage.toString())
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