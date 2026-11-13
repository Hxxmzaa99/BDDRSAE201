package fr.boiteamots.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.zip.GZIPInputStream

/**
 * Charge le dictionnaire français (~319 000 mots, déjà normalisés A-Z, un par ligne)
 * depuis assets/mots.txt.gz. Chargé une seule fois, hors du thread UI.
 */
class DictionaryRepository(private val context: Context) {

    suspend fun load(): List<String> = withContext(Dispatchers.IO) {
        context.assets.open(ASSET).use { raw ->
            GZIPInputStream(raw, 64 * 1024).bufferedReader().useLines { lines ->
                lines.filter { it.isNotEmpty() }.toCollection(ArrayList(330_000))
            }
        }
    }

    private companion object {
        const val ASSET = "mots.txt.gz"
    }
}
