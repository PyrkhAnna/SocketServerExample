package com.epam.web;

import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static final int DEFAULT_PORT = 7777;
	

	// the shutdown command received
	private static boolean shutdown = false;

	public static void main(String[] args) {
		/* Если аргументы отсутствуют, порт принимает значение поумолчанию */
		int port = DEFAULT_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		/* Создаем серверный сокет на полученном порту */
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started on port: " + serverSocket.getLocalPort() + "\n");
		} catch (IOException e) {
			System.out.println("Port " + port + " is blocked.");
			System.exit(-1);
		}
		
		// Loop waiting for a request
		while (!shutdown) {
			//Socket socket = null;
		//	InputStream input = null;
		//	OutputStream output = null;
			try {
				Socket clientSocket = serverSocket.accept();
				/*
				 * Для обработки запроса от каждого клиента создается отдельный
				 * объект и отдельный поток
				 */
				ClientSession session = new ClientSession(clientSocket);
				//new Thread(session).start();
				session.run();
				
				// Close the socket
			//	socket.close();

				// check if the previous URI is a shutdown command
				shutdown = session.ifShutdownCommand();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to establish connection.");
				System.out.println(e.getMessage());
				continue;
			} 

		}
	}

}
