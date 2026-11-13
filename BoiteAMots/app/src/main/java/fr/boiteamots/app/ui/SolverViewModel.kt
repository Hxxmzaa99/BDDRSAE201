package fr.boiteamots.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.boiteamots.app.data.DictionaryRepository
import fr.boiteamots.core.FoundWord
import fr.boiteamots.core.Grid
import fr.boiteamots.core.RandomGrid
import fr.boiteamots.core.Solver
import fr.boiteamots.core.Words
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SolverUiState(
    val loadingDictionary: Boolean = true,
    val dictionarySize: Int = 0,
    val size: Int = 4,
    val letters: String = "",
    val minLength: Int = 3,
    val solving: Boolean = false,
    val results: List<FoundWord>? = null,
    val selected: FoundWord? = null,
) {
    val cellCount get() = size * size
    val isComplete get() = letters.length == cellCount
    val canSolve get() = !loadingDictionary && isComplete && !solving
}

class SolverViewModel(app: Application) : AndroidViewModel(app) {

    private val _state = MutableStateFlow(SolverUiState())
    val state: StateFlow<SolverUiState> = _state.asStateFlow()

    private var solver: Solver? = null
    private var solveJob: Job? = null

    init {
        viewModelScope.launch {
            val words = DictionaryRepository(app).load()
            solver = Solver(words)
            _state.update { it.copy(loadingDictionary = false, dictionarySize = words.size) }
            autoSolveIfReady()
        }
    }

    fun onLettersChange(raw: String) {
        val clean = Words.normalize(raw).take(_state.value.cellCount)
        if (clean == _state.value.letters) return
        solveJob?.cancel()
        _state.update { it.copy(letters = clean, solving = false, results = null, selected = null) }
        autoSolveIfReady()
    }

    fun onSizeChange(size: Int) {
        solveJob?.cancel()
        _state.update {
            it.copy(
                size = size, letters = it.letters.take(size * size),
                solving = false, results = null, selected = null,
            )
        }
        autoSolveIfReady()
    }

    fun onMinLengthChange(min: Int) {
        _state.update { it.copy(minLength = min) }
        if (_state.value.results != null) solve()
    }

    fun randomGrid() = onLettersChange(RandomGrid.letters(_state.value.size))

    fun clear() {
        solveJob?.cancel()
        _state.update { it.copy(letters = "", solving = false, results = null, selected = null) }
    }

    fun select(word: FoundWord?) =
        _state.update { it.copy(selected = if (it.selected == word) null else word) }

    fun solve() {
        val s = _state.value
        val solver = solver ?: return
        if (!s.isComplete) return
        solveJob?.cancel()
        _state.update { it.copy(solving = true, selected = null) }
        solveJob = viewModelScope.launch {
            val results = withContext(Dispatchers.Default) {
                solver.solve(Grid(s.size, s.letters), s.minLength)
            }
            _state.update { it.copy(solving = false, results = results) }
        }
    }

    private fun autoSolveIfReady() {
        if (_state.value.isComplete && !_state.value.loadingDictionary) solve()
    }
}
