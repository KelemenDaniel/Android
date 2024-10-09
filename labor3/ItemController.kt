package labor3

class ItemController(itemService: ItemService) {
    fun quiz(){
        val repo = ItemRepository
        val service = ItemService(repo)
        val items = service.selectRandomItems(3)
        var correctAnswers = 0
        var ok =0
        for(item in items){
            println(item.question)
            for(i in 0..3){
                print(i+1)
                print(") ")
                print(item.answers[i]+"\n")
            }
            print("Enter your answer: ")
            var answ = readln().toIntOrNull()
            while(ok==0) {
                if(answ == null)
                {
                    println("Answer with a number between 1-4!")
                    print("Enter your answer: ")
                    answ = readln().toIntOrNull()
                }
                else if (answ < 1 || answ > 4) {
                    println("Answer with a number between 1-4!")
                    print("Enter your answer: ")
                    answ = readln().toIntOrNull()
                } else {
                    ok = 1
                }
            }
            if(answ == item.correct){
                println("Correct answer!")
                correctAnswers++
            }
            else println("Wrong answer!")
        }
        println("Correct answers: $correctAnswers")
    }
}