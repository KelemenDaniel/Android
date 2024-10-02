package labor2

fun main(){
    //1
//    val dict: IDictionary = DictionaryProvider.createDictionary(DictionaryType.HASH_SET)
//    println("Number of words: ${dict.size()}")
//    var word: String?
//    while(true){
//        print("What to find? ")
//        word = readLine()
//        if( word.equals("quit")){
//            break
//        }
//        println("Result: ${word?.let { dict.find(it) }}")
//    }
    //2
    val name = "John Smith"
    println(name.nameMonogram())

    val list = mutableListOf<String>("apple", "pear", "melon")

    println(list.joinElements("#"))
    println(list.getLongestElement())
}

fun String.nameMonogram(): String{
    return this.split(" ").map { it[0] }.joinToString("")
}

fun List<String>.joinElements(separator: String): String = this.joinToString(separator)

fun List<String>.getLongestElement(): String = this.maxByOrNull{it.length}.toString()