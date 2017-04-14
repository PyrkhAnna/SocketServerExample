package com.epam.web.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.epam.web.Server;

public class GETMessage extends Message{
	private int code;
	
	public GETMessage(String url, String askContentType, List<String> requestBody) {
		super(url, askContentType, requestBody);
	}
	
	public void flushBuffer() throws IOException {
		// private void doGet() throws IOException {
		InputStream strm = Server.class.getResourceAsStream(url);
		code = (strm != null) ? 200 : 404;
		String header;
		String body = getBody();
		if (code == 200) {

			if (body != null) {
				header = getHeader(code);
			} else {
				// file not found
				header = getHeader(404);
			}
			strm.close();
			// }
		//	this.body = body;
		}
	}

	private void doGet() throws IOException {
		InputStream strm = Server.class.getResourceAsStream(url);
		code = (strm != null) ? 200 : 404;
		String header;
		String body = getBody();
		if (code == 200) {

			if (body != null) {
				header = getHeader(code);
			} else {
				// file not found
				header = getHeader(404);
			}
			strm.close();
		}
		//this.body = body;
	}

	private String getHeader(int code) {
		StringBuilder header = new StringBuilder();
		header.append(protocol + code + " " + strCode + "\r\n");
		header.append(date);
		header.append("Accept-Ranges: none\r\n");
		header.append(server);
		header.append(contentLength);
		header.append(contentType);
		header.append(connection);
		return header.toString();
	}

	private String getBody() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		StringBuilder body = new StringBuilder();
		File file = new File(System.getProperty("user.dir") + DEFAULT_FILES_DIR + url+FILE_BASE);
		if (file.exists()) {
			fis = new FileInputStream(file);
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
			if (ch != -1) {
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
	@Override
	public void buildMessage() {
	}
}
