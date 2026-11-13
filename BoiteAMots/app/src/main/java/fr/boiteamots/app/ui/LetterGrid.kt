package fr.boiteamots.app.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Affiche la grille. Si [path] n'est pas vide, les cases du mot sélectionné sont
 * surlignées, numérotées dans l'ordre et reliées par un trait.
 */
@Composable
fun LetterGrid(size: Int, letters: String, path: List<Int>, modifier: Modifier = Modifier) {
    val order = path.withIndex().associate { (i, cell) -> cell to i + 1 }
    val highlight = MaterialTheme.colorScheme.primary
    val gap = 6.dp

    Box(
        modifier
            .widthIn(max = 380.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(gap)) {
            for (r in 0 until size) {
                Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(gap)) {
                    for (c in 0 until size) {
                        val i = r * size + c
                        Cell(
                            letter = letters.getOrNull(i),
                            number = order[i],
                            isStart = path.firstOrNull() == i,
                            modifier = Modifier.weight(1f).fillMaxSize(),
                        )
                    }
                }
            }
        }
        if (path.size > 1) {
            Canvas(Modifier.fillMaxSize()) {
                val gapPx = gap.toPx()
                val cell = (this.size.width - gapPx * (size - 1)) / size
                fun center(i: Int) = Offset(
                    (i % size) * (cell + gapPx) + cell / 2,
                    (i / size) * (cell + gapPx) + cell / 2,
                )
                path.zipWithNext().forEach { (a, b) ->
                    drawLine(
                        color = highlight.copy(alpha = 0.55f),
                        start = center(a), end = center(b),
                        strokeWidth = cell * 0.18f, cap = StrokeCap.Round,
                    )
                }
            }
        }
    }
}

@Composable
private fun Cell(letter: Char?, number: Int?, isStart: Boolean, modifier: Modifier) {
    val scheme = MaterialTheme.colorScheme
    val bg = when {
        isStart -> scheme.primary
        number != null -> scheme.primaryContainer
        else -> scheme.surfaceVariant
    }
    val fg = when {
        isStart -> scheme.onPrimary
        number != null -> scheme.onPrimaryContainer
        else -> scheme.onSurfaceVariant
    }
    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier
            .background(bg, shape)
            .border(1.dp, scheme.outlineVariant, shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter?.toString() ?: "",
            color = fg,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
        )
        if (number != null) {
            Text(
                text = number.toString(),
                color = fg,
                fontSize = 11.sp,
                modifier = Modifier.align(Alignment.TopStart).padding(start = 5.dp, top = 2.dp),
            )
        }
    }
}
