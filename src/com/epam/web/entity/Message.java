package com.epam.web.entity;

import java.util.Date;
import java.util.List;

public abstract class Message {
	protected static final int BUFFER_SIZE = 1024;
	protected static final String DEFAULT_FILES_DIR = "/www";
	protected static final String FILE_BASE = "/booksbase.xml";

	protected String protocol = "HTTP/1.1 ";
	private int code;
	protected String strCode;
	protected String date = "Date: " + new Date().toString() + "\r\n";
	protected String server = "Server: " + System.getProperty("Server.host") + System.getProperty("Server.port")
			+ " (Win32)\r\n";
	protected String contentLength = "Content-Length: ";
	protected String contentType = "Content-Type: ";
	protected String connection = "Connection: Closed\r\n";
	private String emptyLine = "\r\n";
	
	private String body;
	protected String url;
	private List<String> requestBody;

	public Message() {
		super();
	}

	public Message(String url, String askContentType, List<String> requestBody) {
		super();
		this.url = url;
		this.contentType = askContentType;
		this.requestBody = requestBody;
	}

	public abstract void buildMessage();

	
}
