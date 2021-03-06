/*
Simple socket server based on a Github Gist by Steffen
Mogensen (https://gist.github.com/Silverbaq/a14fe6b3ec57703e8cc1a63b59605876)
*/

import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val server = ServerSocket(8070)
    println("Server is running on port ${server.localPort}")

    while (true) {
        val client = server.accept()
        println("Client connected: ${client.inetAddress.hostAddress}")
        thread { ClientHandler(client).run() }
    }

}

class ClientHandler(client: Socket) {
    private val client: Socket = client
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()
    private val calculator: Calculator = Calculator()
    private var running: Boolean = false

    fun run() {
        running = true
        write("Welcome to the server!\n" +
                "To Exit, write: 'EXIT'.\n" +
                "To use the calculator, input two numbers separated with a space and an operation in the ending\n" +
                "Example: 5 33 multi\n" +
                "Available operations: 'add', 'sub', 'div', 'multi'")

        while (running) {
            try {
                val text = reader.nextLine()
                if (text == "EXIT"){
                    shutdown()
                    continue
                }

                val values = text.split(' ')
                val result = calculator.calculate(values[0].toInt(), values[1].toInt(), values[2])
                write(result)
            } catch (ex: Exception) {
                shutdown()
            } finally {
            }
        }
    }

    private fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }
}

class Calculator {

    fun calculate(a: Int, b: Int, operation: String): String {
        when (operation) {
            "add" -> return calc(a, b, ::add).toString()
            "sub" -> return calc(a, b, ::sub).toString()
            "div" -> return calc(a.toDouble(), b.toDouble(), ::div).toString()
            "multi" -> return calc(a, b, ::multi).toString()
            else -> {
                return "Something went wrong"
            }
        }
    }

    private fun <T> calc(a: T, b: T, operation: (T, T) -> T): T {
        return operation(a, b)
    }
    private fun add(a: Int, b: Int): Int = a + b
    private fun sub(a: Int, b: Int): Int = a - b
    private fun div(a: Double, b: Double): Double = a / b
    private fun multi(a: Int, b: Int): Int = a * b

}
