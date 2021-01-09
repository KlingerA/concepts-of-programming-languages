/*
Simple socket client based on a Go sockets article by Alice Williams
(https://dev.to/alicewilliamstech/getting-started-with-sockets-in-golang-2j66)
*/

package main

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"os"
)

const (
	serverHost = "localhost"
	serverPort = "8080"
	serverType = "tcp"
)

func main() {
	fmt.Println("Connecting to", serverType, "server", serverHost + ":" + serverPort)
	conn, err := net.Dial(serverType, serverHost + ":" + serverPort)
	if err != nil {
		fmt.Println("Error connecting:", err.Error())
		os.Exit(1)
	}

	reader := bufio.NewReader(os.Stdin)

	for {
		fmt.Print("Text to send: ")
		input, _ := reader.ReadString('\n')
		conn.Write([]byte(input))
		message, _ := bufio.NewReader(conn).ReadString('\n')
		log.Print("Server relay: " + message)
	}
}
