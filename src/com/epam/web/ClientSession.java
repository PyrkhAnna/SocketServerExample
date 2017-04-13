package com.epam.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;
import java.lang.Runnable;
/**
 * Обрабатывает запрос клиента.
 */
public class ClientSession implements Runnable {

	private Socket socket;
	private InputStream input = null;
	private OutputStream output = null;
	private static final String DEFAULT_FILES_DIR = "/www";
	private static final int BUFFER_SIZE = 1024;
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
		/*try {
			response.sendStaticResource();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
	}
	public boolean ifShutdownCommand() {
		return request.getUri().equals(SHUTDOWN_COMMAND);
		
	}
}
