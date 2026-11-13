package fr.boiteamots.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.boiteamots.core.FoundWord

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolverScreen(vm: SolverViewModel = viewModel()) {
    val state by vm.state.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text("Boîte à Mots — aide") }) }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            item { Controls(state, vm) }
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    LetterGrid(state.size, state.letters, state.selected?.path.orEmpty())
                }
            }
            item {
                state.selected?.let {
                    Text(
                        "${it.word} — commence sur la case 1, suis les numéros",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            results(state, vm)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Controls(state: SolverUiState, vm: SolverViewModel) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            if (state.loadingDictionary) "Chargement du dictionnaire…"
            else "Dictionnaire : ${"%,d".format(state.dictionarySize).replace(',', ' ')} mots",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        SingleChoiceSegmentedButtonRow(Modifier.fillMaxWidth()) {
            listOf(4, 5).forEachIndexed { i, n ->
                SegmentedButton(
                    selected = state.size == n,
                    onClick = { vm.onSizeChange(n) },
                    shape = SegmentedButtonDefaults.itemShape(i, 2),
                ) { Text("$n × $n") }
            }
        }

        OutlinedTextField(
            value = state.letters,
            onValueChange = vm::onLettersChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Lettres, ligne par ligne") },
            supportingText = { Text("${state.letters.length} / ${state.cellCount}") },
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Monospace, letterSpacing = 3.sp,
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                autoCorrectEnabled = false,
                keyboardType = KeyboardType.Password, // pas de suggestions du clavier
                imeAction = ImeAction.Done,
            ),
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Min. ${state.minLength} lettres", Modifier.width(110.dp))
            Slider(
                value = state.minLength.toFloat(),
                onValueChange = { vm.onMinLengthChange(it.toInt()) },
                valueRange = 2f..8f,
                steps = 5,
                modifier = Modifier.weight(1f),
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = vm::randomGrid) { Text("Grille aléatoire") }
            OutlinedButton(onClick = vm::clear) { Text("Effacer") }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
private fun LazyListScope.results(
    state: SolverUiState,
    vm: SolverViewModel,
) {
    when {
        state.solving -> item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                Spacer(Modifier.width(10.dp))
                Text("Recherche…")
            }
        }
        state.results == null -> item {
            if (!state.isComplete) Text(
                "Tape les ${state.cellCount} lettres de la grille : les mots s'affichent automatiquement.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        state.results.isEmpty() -> item { Text("Aucun mot trouvé.") }
        else -> {
            val groups = state.results.groupBy { it.word.length }
            item {
                Text(
                    "${state.results.size} mots trouvés — touche un mot pour voir son chemin",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            items(groups.entries.toList(), key = { it.key }) { (length, words) ->
                Column {
                    Text(
                        "$length lettres (${words.size})",
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        words.forEach { w -> WordChip(w, w == state.selected) { vm.select(w) } }
                    }
                }
            }
        }
    }
}

@Composable
private fun WordChip(word: FoundWord, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(word.word) })
}
