package fr.boiteamots.core

/** Arbre préfixe minimal sur l'alphabet A-Z. */
internal class Trie {
    class Node {
        val children = arrayOfNulls<Node>(26)
        var word: String? = null
    }

    val root = Node()

    fun insert(word: String) {
        var node = root
        for (ch in word) {
            val i = ch - 'A'
            node = node.children[i] ?: Node().also { node.children[i] = it }
        }
        node.word = word
    }
}
