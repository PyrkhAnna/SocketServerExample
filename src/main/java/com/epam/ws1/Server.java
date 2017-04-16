package com.epam.ws1;

import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {

		int port = Const.DEFAULT_PORT;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server started on port: " + serverSocket.getLocalPort() + "\n");
		} catch (IOException e) {
			System.out.println("Port " + port + " is blocked.");
			System.exit(-1);
		}

		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				ClientSession session = new ClientSession(clientSocket);
				new Thread(session).start();
				// session.run();

				// Close the socket
				//serverSocket.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to establish connection.");
				System.out.println(e.getMessage());
				continue;
			}
		}
	}
}
