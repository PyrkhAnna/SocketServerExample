package com.epam.ws1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class Request {
	
	private InputStream input;
	private String methodType;
	private String uri;
	private String protocolVersion;
	private String contentType;
	private String contentLength;
	private String acceptType;
	private String body;
	private Map<String, String> paramSet;

	public Request(InputStream input) {
		this.input = input;
		paramSet = new HashMap<String, String>();
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getAcceptType() {
		return acceptType;
	}

	public void setAcceptType(String acceptType) {
		this.acceptType = acceptType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void parse() throws IOException {
		List<String> header = new ArrayList<String>();
		StringBuffer body = new StringBuffer();
		String[] str = flushBuffer();
		int i = 0;
		boolean flag = false;
		while (i < str.length) {
			if (str[i].isEmpty()) {
				i++;
				flag = true;
			}
			if (!flag) {
				header.add(str[i]);
			} else {
				body.append(str[i]);
			}
			i++;
		}
		methodType=getMethod(header.get(0).trim());
		uri=getURIFromHeader(str[0]);
		protocolVersion = Const.PROTOCOL;
		contentType = getFromHeader(header, Const.CONTENT_TYPE);
		contentLength = getFromHeader(header, Const.CONTENT_LENGTH);
		acceptType = getFromHeader(header, Const.ACCEPT_TYPE);
		this.body = body.toString();
				
		paramSet.put(Const.CONTENT_TYPE, contentType);
		paramSet.put(Const.CONTENT_LENGTH, contentLength);
		paramSet.put(Const.ACCEPT_TYPE, acceptType);
		paramSet.put(Const.URI, uri);
		paramSet.put(Const.METHOD_TYPE, methodType);
		paramSet.put(Const.BODY, this.body);
		System.out.println(paramSet.get(Const.METHOD_TYPE));
		System.out.println(paramSet.get(Const.URI));
		System.out.println(paramSet.get(Const.BODY));
	}

	private String[] flushBuffer() {
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
		System.out.println(request.toString());
		return request.toString().split("\r\n");
	}

	private String getURIFromHeader(String header) {
		int from = header.indexOf(" ") + 1;
		int to = header.indexOf(" ", from);
		String uri = null;
		if (to!=-1) {
		 uri = header.substring(from, to);
		}
		return uri;
	}

	private String getFromHeader(List<String> header, String marker) {
		int from;
		String value = null;
		for (String s : header) {
			if (s.startsWith(marker)) {
				from = s.indexOf(" ") + 1;
				value = s.substring(from, s.length());
			}
		}
		return value;
	}

	private String getMethod(String str) {
		int to = str.indexOf(" ");
		return str.substring(0, to);
	}

	public Map<String, String> getParametersSet() {
		return paramSet;
	}
}