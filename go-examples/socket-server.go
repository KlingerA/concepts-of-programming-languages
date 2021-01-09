/*
Simple socket server based on a Go sockets article by Alice Williams
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
	sHost  = "localhost"
	sPort  = "8080"
	sType = "tcp"
)

func main() {
	fmt.Println("Starting " + sType + " server on " + sHost + ":" + sPort)
	l, err := net.Listen(sType, sHost + ":" + sPort)
	if err != nil {
		fmt.Println("Error listening:", err.Error())
		os.Exit(1)
	}
	defer l.Close()

	for {
		c, err := l.Accept()
		if err != nil {
			fmt.Println("Error connecting:", err.Error())
			return
		}
		fmt.Println("Client connected.")
		fmt.Println("Client " + c.RemoteAddr().String() + " connected.")
		go handleConnection(c)
	}
}

func handleConnection(conn net.Conn) {
	buffer, err := bufio.NewReader(conn).ReadBytes('\n')
	if err != nil {
		fmt.Println("Client left.")
		conn.Close()
		return
	}
	log.Println("Client message:", string(buffer[:len(buffer)-1]))
	conn.Write(buffer)
	handleConnection(conn)
}
