/*
Goroutines example from the Go tutorial by Naveen
Ramanathan (https://golangbot.com/goroutines/)
converted to Kotlin
*/

import kotlinx.coroutines.*

fun numbers() {
    GlobalScope.launch {
        for (i in 1..5) {
            delay(250)
            print("$i ")
        }
    }
}
fun alphabets() {
    val letters = arrayOf("a", "b", "c", "d", "e")
    GlobalScope.launch {
        for (i in 0..4) {
            delay(400)
            print(letters[i] + " ")
        }
    }
}
fun main(args: Array<String>) {
    numbers()
    alphabets()
    Thread.sleep(3000)
    println("main terminated")
}
