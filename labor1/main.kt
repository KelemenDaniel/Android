package labor1
import java.util.*
import kotlin.random.Random
//1
// fun main() {
// 	var sum = 2 + 3
//     print("sum = $sum\n")
//     print("sum_2 = $(2 + 3)")
// }

//2
// fun main(){
//     val daysOfWeek = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")
//     for(day: String in daysOfWeek){
//         println(day)
//     }
//     println("............................")
//     daysOfWeek.filter { it.startsWith("t") }.forEach { println(it) }
//     println("............................")
//     daysOfWeek.filter { it.contains("e") }.forEach { println(it) }
//     println("............................")
//     daysOfWeek.filter { it.length == 6 }.forEach { println(it) }
// }

//3
// fun isPrime(num: Int): Boolean {
//     if (num <= 1) return false
//     for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
//         if (num % i == 0) return false
//     }
//     return true
// }

// fun printPrimesInRange(start: Int, end: Int) {
//     for (i in start..end) {
//         if (isPrime(i)) {
//             println(i)
//         }
//     }
// }

// fun main() {
//     val startRange = 10
//     val endRange = 50
//     printPrimesInRange(startRange, endRange)
// }


//4
// fun encodeString(msg: String): String{
//     return Base64.getEncoder().encodeToString(msg.toByteArray())
// }
// fun decodeString(msg: String): String{
//     return String(Base64.getDecoder().decode(msg))
// }
// fun messageCoding(msg: String, func:(String) -> String):String{
// 	return func(msg)
// }
// fun main(){
//     val message = "Ez egy uzenet!"
//     val encodedmessage = messageCoding(message, ::encodeString)
//     println(encodedmessage)
//     val decodedMessage = messageCoding(encodedmessage, ::decodeString)
//     println(decodedMessage)
// }

//5
// fun printEvenNumbers(numbers: IntArray)=numbers.filter{(it%2==0)}.forEach{ println(it)}
// fun main(){
//     val numbers = intArrayOf(1,2,3,4,5,6,7,8,9,10)
// 	printEvenNumbers(numbers)
// }

//6
// fun main() {
//     val numbers = listOf(1, 2, 3, 4, 5)
//     val doubledNumbers = numbers.map { it * 2 }
//     println("Doubled Numbers: $doubledNumbers")
// 	println("............................")

//     val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
//     val capitalizedDays = daysOfWeek.map { it.uppercase() }
//     println("Capitalized Days: $capitalizedDays")
// 	println("............................")

//     val firstChars = daysOfWeek.map { it[0].lowercaseChar() }
//     println("First characters capitalized: $firstChars")
// 	println("............................")

//     val lengthsOfDays = daysOfWeek.map { it.length }
//     println("Length of Days: $lengthsOfDays")
// 	println("............................")

//     val averageLength = lengthsOfDays.average()
//     println("Average Length of Days: $averageLength")
// }


//7
// fun main() {
//     val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

//     val mutableDaysOfWeek = daysOfWeek.toMutableList()
// 	println("............................")

//     mutableDaysOfWeek.removeAll { it.contains('n', ignoreCase = true) }
//     println("Mutable list after removing days containing 'n': $mutableDaysOfWeek")
// 	println("............................")

//     for ((index, day) in mutableDaysOfWeek.withIndex()) {
//         println("Item at $index is $day")
//     }
// 	println("............................")
//     mutableDaysOfWeek.sort()
//     println("Sorted list alphabetically: $mutableDaysOfWeek")
// }

//8
fun main() {
    val randomArray = Array(10) { Random.nextInt(0, 101) }

    println("Generated array:")
    randomArray.forEach { println(it) }
    println("............................")

    val sortedArray = randomArray.sortedArray()
    println("Sorted array in ascending order:")
    sortedArray.forEach { println(it) }

    println("............................")
    val containsEven = randomArray.any { it % 2 == 0 }
    println("Does the array contain any even number? $containsEven")

    println("............................")
    val allEven = randomArray.all { it % 2 == 0 }
    println("Are all numbers even? $allEven")

    println("............................")
    val average = randomArray.average()
    println("Average of the generated numbers: $average")
}

