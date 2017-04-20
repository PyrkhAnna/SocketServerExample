package com.epam.ws1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static boolean shutdown = false;

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

		while (!shutdown) {
			try {
				//InputStream input = null;
				//OutputStream output = null;

				Socket clientSocket = serverSocket.accept();
				ClientSession session = new ClientSession(clientSocket);
				//new Thread(session).start();
				 session.run();

				// Close the socket
				clientSocket.close();
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
