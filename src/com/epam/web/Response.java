package com.epam.web;

import java.io.OutputStream;
import java.util.Date;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {
	private static final String DEFAULT_FILES_DIR = "/www";
	private static final int BUFFER_SIZE = 1024;
	private Request request;
	private OutputStream output;
	private String message;

	public Response(OutputStream output) {
		this.output = output;
		message = null;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void sendMessage() {
		try {
			formMessage();
			if (message!=null) {
				output.write(message.getBytes());
				message=null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void formMessage() throws IOException {
		String url = request.getUrl();
		InputStream strm = Server.class.getResourceAsStream(url);
		int code = (strm != null) ? 200 : 404;
		String header = getHeader(code);
		if (code == 200) {
			String body = getBody();
			if (body != null) {
				message=header+body;
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				message=errorMessage;
			}
			strm.close();
		}
	}

	private String getHeader(int code) {
		StringBuilder header = new StringBuilder();
		header.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\r\n");
		header.append("Date: " + new Date().toString() + "\r\n");
		header.append("Accept-Ranges: none\n");
		return header.toString();
	}

	private String getBody() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		StringBuilder body = new StringBuilder();
		File file = new File(System.getProperty("user.dir") + DEFAULT_FILES_DIR + request.getUrl());
		if (file.exists()) {
			fis = new FileInputStream(file);
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			if (ch != -1) {
				body.append("Content-Length: " + ch + "\r\n");
				body.append("Content-Type: application/xml\r\n");
				body.append("\r\n");
				for (int j = 0; j < ch; j++) {
					body.append((char) bytes[j]);
				}
			}
		} else {
			return null;
		}
		if (fis != null)
			fis.close();
		return body.toString();
	}

	private String getAnswer(int code) {
		switch (code) {
		case 200:
			return "OK";
		case 404:
			return "Not Found";
		default:
			return "Internal Server Error";
		}
	}

}