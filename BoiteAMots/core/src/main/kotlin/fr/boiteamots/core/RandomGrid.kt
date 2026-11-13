package fr.boiteamots.core

import kotlin.random.Random

/** Génère une grille aléatoire pondérée par la fréquence des lettres en français (pour s'entraîner). */
object RandomGrid {
    private const val WEIGHTED =
        "EEEEEEEEEEEEEEEAAAAAAAAAIIIIIIIISSSSSSNNNNNNRRRRRRTTTTTTOOOOOOLLLLLUUUUUU" +
        "DDDCCCMMMPPGGBBVVHHFFQJKWXYZ"

    fun letters(size: Int, random: Random = Random.Default): String =
        buildString { repeat(size * size) { append(WEIGHTED[random.nextInt(WEIGHTED.length)]) } }
}
