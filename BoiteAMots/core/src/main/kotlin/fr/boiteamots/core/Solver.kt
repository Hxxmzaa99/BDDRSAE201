package fr.boiteamots.core

/** Un mot trouvé et le chemin (indices de cases, dans l'ordre) qui le forme. */
data class FoundWord(val word: String, val path: List<Int>)

/**
 * Trouve tous les mots du dictionnaire constructibles dans la grille en reliant
 * des cases adjacentes (8 directions) sans réutiliser une case dans un même mot.
 *
 * Stratégie (rapide et économe en mémoire) :
 *  1. Filtrage du dictionnaire : on ne garde que les mots dont les lettres
 *     "tiennent" dans la grille (comptage des lettres) -> quelques milliers de mots.
 *  2. Construction d'un petit Trie avec ces candidats.
 *  3. Parcours en profondeur depuis chaque case, élagué par le Trie.
 *
 * @param dictionary mots déjà normalisés (A-Z, majuscules).
 */
class Solver(private val dictionary: List<String>) {

    fun solve(grid: Grid, minLength: Int = 3): List<FoundWord> {
        val trie = buildCandidateTrie(grid, minLength)
        val found = LinkedHashMap<String, FoundWord>()
        val visited = BooleanArray(grid.cellCount)
        val path = IntArray(grid.cellCount)

        fun dfs(cell: Int, parent: Trie.Node, depth: Int) {
            val node = parent.children[grid[cell] - 'A'] ?: return
            visited[cell] = true
            path[depth] = cell
            node.word?.let { w ->
                if (w !in found) found[w] = FoundWord(w, path.copyOf(depth + 1).toList())
            }
            for (next in grid.neighbors[cell]) {
                if (!visited[next]) dfs(next, node, depth + 1)
            }
            visited[cell] = false
        }

        for (start in 0 until grid.cellCount) dfs(start, trie.root, 0)

        return found.values.sortedWith(
            compareByDescending<FoundWord> { it.word.length }.thenBy { it.word }
        )
    }

    private fun buildCandidateTrie(grid: Grid, minLength: Int): Trie {
        val available = IntArray(26)
        for (ch in grid.cells) available[ch - 'A']++
        val maxLength = grid.cellCount

        val trie = Trie()
        val counts = IntArray(26)
        for (word in dictionary) {
            if (word.length < minLength || word.length > maxLength) continue
            counts.fill(0)
            var ok = true
            for (ch in word) {
                val i = ch - 'A'
                if (i !in 0..25 || ++counts[i] > available[i]) { ok = false; break }
            }
            if (ok) trie.insert(word)
        }
        return trie
    }
}
