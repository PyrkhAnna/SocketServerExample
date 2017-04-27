package com.epam.ws1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.lang.Runnable;

public class ClientSession implements Runnable {
	private Socket socket;
	private InputStream input = null;
	private OutputStream output = null;
	private Request request;
	private Response response;

	public ClientSession(Socket socket) throws IOException {
		this.socket = socket;
		initialize();
	}

	private void initialize() throws IOException {
		input = socket.getInputStream();
		output = socket.getOutputStream();
		// run();
	}

	@Override
	public void run() {
		request = new Request(input);
		try {
			request.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response = new Response(output);
		response.setRequest(request);
		response.sendMessage();
	}

	public boolean ifShutdownCommand() {
		return request.getUri().equals(Const.SHUTDOWN_COMMAND);
	}
}
