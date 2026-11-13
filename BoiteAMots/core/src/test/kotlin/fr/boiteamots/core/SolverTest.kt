package fr.boiteamots.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SolverTest {

    // C A T
    // X R E
    // X X S
    private val grid = Grid(3, "CATXREXXS")

    @Test
    fun `trouve les mots en suivant des cases adjacentes`() {
        val result = Solver(listOf("CAT", "CARE", "CARES", "TARES", "RATE")).solve(grid)
        val words = result.map { it.word }.toSet()
        assertEquals(setOf("CAT", "CARE", "CARES", "TARES", "RATE"), words)
    }

    @Test
    fun `ne reutilise pas une case dans un meme mot`() {
        // "CAC" demanderait de repasser par le C.
        val result = Solver(listOf("CAC", "ACA")).solve(grid)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `refuse les lettres non adjacentes`() {
        // C (0) et S (8) ne sont pas voisins.
        val result = Solver(listOf("CS", "CSX")).solve(grid, minLength = 2)
        assertTrue(result.isEmpty())
    }

    @Test
    fun `le chemin renvoye est valide`() {
        val found = Solver(listOf("CARES")).solve(grid).single()
        assertEquals("CARES", found.path.map { grid[it] }.joinToString(""))
        assertEquals(found.path.size, found.path.toSet().size)
        found.path.zipWithNext().forEach { (a, b) -> assertTrue(b in grid.neighbors[a]) }
    }

    @Test
    fun `respecte la longueur minimale et trie du plus long au plus court`() {
        val result = Solver(listOf("CAT", "CARES", "CARE")).solve(grid, minLength = 4)
        assertEquals(listOf("CARES", "CARE"), result.map { it.word })
        assertFalse(result.any { it.word == "CAT" })
    }

    @Test
    fun `normalise les accents`() {
        assertEquals("ELEVEOEUF", Words.normalize("Élève-œuf"))
        assertEquals("ABCE", Grid(2, "a-b cé").cells.concatToString())
    }
}
