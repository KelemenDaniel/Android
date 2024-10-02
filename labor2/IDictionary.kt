package labor2

interface IDictionary {
    fun add(word: String): Boolean
    fun find(word: String): Boolean
    fun size(): Int

    companion object{
        const val DICTIONARY_NAME = "D:\\AndroidGyak\\labor2\\resources\\adatok.txt"
    }
}