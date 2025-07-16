
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
import org.albarmajy.traffic.view.ReportsView
import view.QuestionsView
import view.VehicleView
import viewModel.QuestionsViewModel
import viewModel.ReportViewModel
import viewModel.VehicleViewModel


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
            size = DpSize(width = 900.dp, height = 650.dp)
        ),
        icon = painterResource("image/icon.png"),
        resizable = true
    ) {
        AppTheme {

            var switch by remember { mutableStateOf(2) }
            val viewModel = remember { ReportViewModel() }
            val viewModel2 = remember { VehicleViewModel() }
            val viewModel3 = remember { QuestionsViewModel() }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                when(switch){
                    1 -> ReportsView(viewModel){ switch++}
                    2 -> VehicleView(viewModel2, onNextClicked = { switch++ }, onBackClicked = {switch--})
                    3 -> QuestionsView(viewModel3, onNextClicked = { switch++ }, v= viewModel2, r = viewModel, onBackClicked = {switch--})

                }
            }

        }
    }
}


