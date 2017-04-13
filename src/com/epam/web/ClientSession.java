package com.epam.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.Runnable;
/**
 * Обрабатывает запрос клиента.
 */
public class ClientSession implements Runnable {

	private Socket socket;
	private InputStream input = null;
	private OutputStream output = null;
	private Request request;
	private Response response;
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	

	public ClientSession(Socket socket) throws IOException {
		this.socket = socket;
		initialize();
	}

	private void initialize() throws IOException {
		/* Получаем поток ввода, в который помещаются сообщения от клиента */
		input = socket.getInputStream();
		/* Получаем поток вывода, для отправки сообщений клиенту */
		output = socket.getOutputStream();
	}

	@Override
	public void run() {
		// create Request object and parse
		request = new Request(input);
		
		try {
			request.parse();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create Response object
		response = new Response(output);
		response.setRequest(request);
		response.sendMessage();
		
	}
	
	public boolean ifShutdownCommand() {
		return request.getUrl().equals(SHUTDOWN_COMMAND);
		
	}
}
