/*
Simple socket client based on a Github Gist by Steffen
Mogensen (https://gist.github.com/Silverbaq/1fdaf8aee72b86b8c9e2bd47fd1976f4)
*/

import java.io.OutputStream
import java.net.Socket
import java.nio.charset.Charset
import java.util.*
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    val host = "localhost"
    val port = 8070
    val client = Client(host, port)
    client.run()
}

class Client(host: String, port: Int) {
    private val connection: Socket = Socket(host, port)
    private var connected: Boolean = true

    init {
        println("Connected to server at $host on port $port")
    }

    private val reader: Scanner = Scanner(connection.getInputStream())
    private val writer: OutputStream = connection.getOutputStream()

    fun run() {
        thread { read() }
        while (connected) {
            val input = readLine() ?: ""
            if ("exit" in input) {
                connected = false
                reader.close()
                connection.close()
            } else {
                write(input)
            }
        }
    }

    private fun write(message: String) {
        writer.write((message + '\n').toByteArray(Charset.defaultCharset()))
    }

    private fun read() {
        while (connected)
            println(reader.nextLine())
    }
}
