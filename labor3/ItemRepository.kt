package labor3

import java.io.File

object ItemRepository {
    private var items = mutableListOf<Item>()
    init{
        val lines = File("D:\\AndroidGyak\\labor3\\questions.txt").useLines{it.toList()}

        for(i in lines.indices step 6){
            val question=lines[i]

            val ans1 = lines[i+1]
            val ans2 = lines[i+2]
            val ans3 = lines[i+3]
            val ans4 = lines[i+4]
            val correct = lines[i+5]

            save(Item(question, mutableListOf(ans1,ans2,ans3,ans4), correct.toInt()))
        }
    }
    fun randomItem() = items.random()
    fun save(item: Item){
        items.add(item)
    }
    fun size()=items.size
}