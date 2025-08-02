
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.AllData
import org.albarmajy.traffic.view.ReportsView
import utiity.chooseSaveFilePath
import utiity.createDocxUsingPython
import view.*
import viewModel.QuestionsViewModel
import viewModel.ReportViewModel
import viewModel.VehicleViewModel
import java.io.File
import java.time.LocalDate


@Composable
@Preview
fun App() {
    AppTheme {
        var vm = ReportViewModel()
        ReportsView(vm){}
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        title = "محضر النيابة",
        state = WindowState(
            size = DpSize(width = 900.dp, height = 650.dp),

        ),
        icon = painterResource("image/icon.png"),
        resizable = true
    ) {
        AppTheme {

            var switch by remember { mutableStateOf(1) }
            val viewModel = remember { ReportViewModel() }
            val viewModel2 = remember { VehicleViewModel() }
            val viewModel3 = remember { QuestionsViewModel() }

            var action by remember { mutableStateOf(1) }

            val allData = AllData(
                date = viewModel.prosecutionDate,
                time = viewModel.formatToArabicTime(viewModel.prosecutionTime),
                pro = viewModel.prosecutor.text,
                sec = viewModel.secretary.text,
                num = viewModel.caseNumber.text,
                year = viewModel.prosecutionDate.substring(0, 4),
                department = viewModel.policeDepartment.toString(),
                cap_title = viewModel.officerRank.toString(),
                cap_name = viewModel.officerName.text,
                d_date = viewModel.reportDate.text,
                d_time = viewModel.formatToArabicTime(viewModel.reportTime.text),
                car_type = viewModel2.vehicleType.toString(),
                plate = viewModel2.plate.text,
                unit = viewModel2.mror.toString(),
                owner = viewModel2.owner.text,
                name = viewModel2.name.text,
                address = viewModel2.address.text,
                age = viewModel2.getAge().toString(),
                job = viewModel2.job.text,
                id = viewModel2.nationalId.text,
                questions = viewModel3.qAndAList.map { it.toQandAData() },
                decisions = viewModel3.getDecisions(),
                instructions = viewModel3.getInstructions(),
                action = action,
            )
            val dataMap = mapOf(
                "date" to viewModel.prosecutionDate,
                "time" to viewModel.prosecutionTime,
                "pro" to viewModel.prosecutor.text,
                "sec" to viewModel.secretary.text,
                "number" to viewModel.caseNumber.text,
                "year" to viewModel.prosecutionDate.substring(0,4),
                "cap_title" to viewModel.officerRank.toString(),
                "cap_name" to viewModel.officerName.text,
                "department" to viewModel.policeDepartment.toString(),
                "d_date" to viewModel.reportDate.text,
                "d_time" to viewModel.reportTime.text,
                "vehicle" to viewModel2.vehicleType.toString(),
                "plate" to viewModel2.plate.text,
                "v_mror" to viewModel2.mror.toString(),
                "v_owner" to viewModel2.owner.text,
                "name" to viewModel2.name.text,
                "address" to viewModel2.address.text,
                "age" to viewModel2.getAge().toString(),
                "job" to viewModel2.job.text,
                "id" to viewModel2.nationalId.text,
            )
            
            var outputPath = "E:/pook5.docx"
            var feedpack by remember { mutableStateOf(true) }
            var onSave by remember { mutableStateOf(false) }
            var statusMessage by remember { mutableStateOf("") }


            if (onSave){
                val outputPath2 = chooseSaveFilePath(
                    title = "حفظ التقرير",
                    defaultFileName = "${viewModel2.name.text} ${LocalDate.now()}.docx",
                    allowedExtensions = listOf("docx")
                )
                @Suppress("OPT_IN_USAGE")
                with(rememberCoroutineScope()) {
                    launch(Dispatchers.IO) { // Use IO dispatcher for file operations and process execution
                        val success = createDocxUsingPython("src/main/resources/templates/temp1.docx",outputPath2.toString(), allData)
                        if (success) {
                            statusMessage = "تم إنشاء المحضر بنجاح: ${File("outputFilename").absolutePath}"
                        } else {
                            statusMessage = "فشل إنشاء المحضر. يرجى مراجعة السجلات."
                        }
                    }
                }
                onSave = false
                println(statusMessage)
            }





            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                when(switch){
                    1 -> ReportsView(viewModel){ switch++}
                    2 -> VehicleView(viewModel2, onNextClicked = { switch++ }, onBackClicked = {switch--})
                    3 -> QuestionsView(viewModel3, onNextClicked = { switch++ }, v= viewModel2, r= viewModel, onBackClicked = {switch--})
                    4 -> DecisionView(viewModel3, onNextClicked = { switch++ }, v= viewModel2, onBackClicked = {switch--} )
                    5 -> InstructionView(viewModel3, onNextClicked = { switch++ }, v= viewModel2, onBackClicked = {switch--} )
                    6 -> FinishView(
                        onSave = {
                            action = 1
                           onSave = true
                         },
                        onPrint = {
                            action = 3

                            onSave = true
                        },
                        onWord = {
                            action = 2
                            onSave = true
                        }
                    )

                }
            }

        }
    }
}


