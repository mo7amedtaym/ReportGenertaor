package viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.Decision
import model.QandA
import model.QandAData

class QuestionsViewModel() {

    private val _qAndAList = mutableStateListOf<QandA>()
    val qAndAList: SnapshotStateList<QandA> = _qAndAList

    private val _decisionList = mutableStateListOf<Decision>()
    var decisionList: SnapshotStateList<Decision> = _decisionList

    private val _instructionList = mutableStateListOf<Decision>()
    var instructionList: SnapshotStateList<Decision> = _instructionList

    var isFirst by mutableStateOf(true)
    var isFirst2 by mutableStateOf(true)
    var isFirst3 by mutableStateOf(true)

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


    fun resetQuestions(l:List<QandA>) {
        _qAndAList.clear()
        _qAndAList.addAll(l)
    }

    fun getQuestions(): List<QandAData> {
        return _qAndAList.map { it.toQandAData() }
    }

    fun getDecisions(): List<String> {
        var i = 0
        return _decisionList.map {
            (labels[i++]+"/ "+it.toString() )
        }
    }
    fun getInstructions(): List<String> {
        var i = 0
        return _instructionList.map {
            (labels[i++]+"/ "+it.toString() )
        }
    }

    fun addQuestions(qa: List<QandA>) {

        if (isFirst){
            _qAndAList.addAll(qa)
            isFirst = false
        }

    }
    fun addDecisions(qa: List<Decision>) {

        if (isFirst2){
            _decisionList.addAll(qa)
            isFirst2 = false
        }

    }
    fun addInstructions(qa: List<Decision>) {

        if (isFirst3){
            _instructionList.addAll(qa)
            isFirst3 = false
        }

    }

    var errors by mutableStateOf<Map<String, String>>(emptyMap())
    var showErrorSnackbar by mutableStateOf(false)
    var snackbarMessage by mutableStateOf("")

    fun showError(message: String) {
        snackbarMessage = message
        showErrorSnackbar = true
    }

    fun dismissSnackbar() {
        showErrorSnackbar = false
    }

    fun validateAll(): Boolean {
        val newErrors = mutableMapOf<String, String>()
        errors = newErrors
        return newErrors.isEmpty()
    }

    fun addItem() {
        _qAndAList.add(QandA())
    }
    fun addItemDecision() {
        _decisionList.add(Decision())
    }
    fun addItemDecision(index: Int) {
        _decisionList.add(index,Decision())
    }

    fun addItemInstruction() {
        _instructionList.add(Decision())
    }
    fun addItemInstruction(index: Int) {
        _instructionList.add(index,Decision())
    }


    fun addItem(index: Int) {
        _qAndAList.add(index, QandA())
    }

    fun removeItem(index: Int) {
        if (index in _qAndAList.indices) {
            _qAndAList.removeAt(index)
        }
    }
    fun removeItemDecision(index: Int) {
        if (index in _decisionList.indices) {
            _decisionList.removeAt(index)
        }
    }
    fun removeItemInstruction(index: Int) {
        if (index in _decisionList.indices) {
            _instructionList.removeAt(index)
        }
    }
}
