package viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import model.QandA

class QuestionsViewModel() {

    private val _qAndAList = mutableStateListOf<QandA>()
    val qAndAList: SnapshotStateList<QandA> = _qAndAList
    var isFirst by mutableStateOf(true)




//    init {
//        resetQuestions()
//    }

    fun resetQuestions(l:List<QandA>) {
        _qAndAList.clear()
        _qAndAList.addAll(l)
    }

    fun addQuestions(qa: List<QandA>) {

        if (isFirst){
            _qAndAList.addAll(qa)
            isFirst = false
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

    fun addItem(index: Int) {
        _qAndAList.add(index, QandA())
    }

    fun removeItem(index: Int) {
        if (index in _qAndAList.indices) {
            _qAndAList.removeAt(index)
        }
    }
}
