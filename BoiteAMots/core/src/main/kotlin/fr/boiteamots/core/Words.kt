package fr.boiteamots.core

import java.text.Normalizer

object Words {
    private val diacritics = Regex("\\p{Mn}+")
    private val nonLetters = Regex("[^A-Z]")

    /** "Élève-Œuf" -> "ELEVEOEUF" : majuscules, sans accents, lettres A-Z uniquement. */
    fun normalize(input: String): String {
        val expanded = input.replace("œ", "oe").replace("Œ", "OE")
            .replace("æ", "ae").replace("Æ", "AE")
        return Normalizer.normalize(expanded, Normalizer.Form.NFD)
            .replace(diacritics, "")
            .uppercase()
            .replace(nonLetters, "")
    }
}
