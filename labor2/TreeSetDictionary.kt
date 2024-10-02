package labor2

import java.io.File
import java.util.TreeSet

object TreeSetDictionary: IDictionary {

    private val words = TreeSet<String>()

    init {
        File(IDictionary.DICTIONARY_NAME).forEachLine { add(it) }
    }

    override fun add(word: String) = words.add(word)

    override fun find(word: String) = words.find{it == word} != null

    override fun size() = words.size
}