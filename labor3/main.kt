package labor3

fun main() {
    val repo = ItemRepository
    val quizItem = repo.randomItem()
    println("Question: " + quizItem.question)
    println("--------------------------")
    val service = ItemService(repo)
    val quizItems = service.selectRandomItems(3)
    for (item in quizItems){
        println("Question: " + item.question)
    }
    println("--------------------------")
    val controller = ItemController(service)
    controller.quiz()
}