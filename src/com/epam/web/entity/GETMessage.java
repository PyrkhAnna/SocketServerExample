package com.epam.web.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.web.Server;

public class GETMessage extends Message{
	private int code;
	private String uri;
	private Map<String,String> param;
	private String askContentType;
	
	public GETMessage(String uri, String askContentType) {
		super();
		this.uri= uri;
		this.askContentType = askContentType;
	}
	private boolean checkUri() {
		
		return false;
	}
	public void flushBuffer() throws IOException {
		InputStream strm = Server.class.getResourceAsStream(getUrlFromUri(uri));
		code = (strm != null) ? 200 : 404;
		
		String header;
		String body = getBody();
		if (code == 200) {
			if (body != null) {
				header = getHeader(code);
			} else {
				// file not found
				header = getErrorMessage();
			}
			strm.close();
		} else {
			// file not found
			header = getErrorMessage();
		}
	}
	private String getUrlFromUri(String uri) {
		String url [] = uri.split("?");
		if (url.length>1) {
			getParam(url);
		}
		return url[0];
	}
	private void getParam(String [] url) {
		param = new HashMap<String,String>();
		String[] str;
		for (int i =1; i> url.length;i++) {
			str = url[i].split("=");
			param.put(str[0], str[1]);
		}
	}
	private String getErrorMessage() {
		return "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
				+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
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
