package com.epam.web.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;

import com.epam.web.Server;

public class Message {
	private static final int BUFFER_SIZE = 1024;
	private static final String DEFAULT_FILES_DIR = "/www";
	private static final String FILE_BASE = "/booksbase.xml";
	
	private String protocol = "HTTP/1.1 ";
	private int code;
	private String strCode;
	private String date = "Date: " + new Date().toString() + "\r\n";
	private String server = "http://localhost:port (Win32)\r\n";
	private String contentLength = "Content-Length: ";
	private String contentType = "Content-Type: ";
	private String connection = "Connection: Closed\r\n";
	private String emptyLine = "\r\n";
	private String body;
	private String method;
	private String url;
	// private String askContentType;
	private List<String> requestBody;

	public Message(String method, String url, String askContentType, List<String> requestBody) {
		super();
		this.method = method;
		this.url = url;
		this.contentType = askContentType;
		this.requestBody = requestBody;
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
			this.body = body;
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
		this.body = body;
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

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

}
