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

public abstract class Message {
	protected static final int BUFFER_SIZE = 1024;
	protected static final String DEFAULT_FILES_DIR = "/www";
	protected static final String FILE_BASE = "/booksbase.xml";
	
	protected String protocol = "HTTP/1.1 ";
	private int code;
	protected String strCode;
	protected String date = "Date: " + new Date().toString() + "\r\n";
	protected String server = "Server: Server (Win32)\r\n";
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
