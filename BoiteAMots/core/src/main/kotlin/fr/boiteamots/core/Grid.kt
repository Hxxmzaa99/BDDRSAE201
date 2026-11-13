package fr.boiteamots.core

/**
 * Grille carrée de lettres (A-Z), stockée ligne par ligne.
 * Les voisins (8 directions) sont précalculés une seule fois.
 */
class Grid(val size: Int, letters: String) {

    val cells: CharArray

    /** neighbors[i] = indices des cases adjacentes à la case i. */
    val neighbors: Array<IntArray>

    init {
        require(size in 2..8) { "Taille de grille invalide : $size" }
        val clean = Words.normalize(letters)
        require(clean.length == size * size) {
            "Il faut ${size * size} lettres, reçu ${clean.length}"
        }
        cells = clean.toCharArray()
        val n = size // ne pas utiliser `size` dans buildList : il désignerait la taille de la liste
        neighbors = Array(n * n) { i ->
            val r = i / n
            val c = i % n
            buildList {
                for (dr in -1..1) for (dc in -1..1) {
                    if (dr == 0 && dc == 0) continue
                    val nr = r + dr
                    val nc = c + dc
                    if (nr in 0 until n && nc in 0 until n) add(nr * n + nc)
                }
            }.toIntArray()
        }
    }

    val cellCount: Int get() = size * size

    operator fun get(index: Int): Char = cells[index]
}
