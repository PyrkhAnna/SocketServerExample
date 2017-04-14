package com.epam.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class Request {
	private final static String CONTENT_TYPE = "Content-Type:";
	private final static String CONTENT_LENGTH = "Content-Length:";
	private final static String ACCEPT_TYPE = "Accept:";
	//private final static String METHOD_TYPE = "methodType";
	//private final static String URI = "uri";
	private InputStream input;
	private String methodType;
	private String uri;
	private List<String> body;
	private Map<String, String> headerFields;

	public Request(InputStream input) {
		this.input = input;
		body = new ArrayList<String>();
		headerFields = new HashMap<String, String>();
	}

	public void parse() throws IOException {
		List<String> header = new ArrayList<String>();
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
				//body.add(str[i]);
			}
			i++;
		}
		// System.out.println(header.toString());
		// System.out.println(body.toString());
		headerFields.put(CONTENT_TYPE, getFromHeader(header, CONTENT_TYPE));
		headerFields.put(CONTENT_LENGTH, getFromHeader(header, CONTENT_LENGTH));
		headerFields.put(ACCEPT_TYPE, getFromHeader(header, ACCEPT_TYPE));
		//headerFields.put(URI, getURIFromHeader(str[0]));
		//headerFields.put(METHOD_TYPE, getMethod(header.get(0).trim()));
		uri = getURIFromHeader(str[0]);
		methodType = getMethod(header.get(0).trim());
		System.out.println(uri);
		System.out.println(methodType);
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
		String uri;
		if (to!=-1) {
		 uri = header.substring(from, to);
		} else 
			uri = header.substring(from, header.length()-1);
		int paramIndex = uri.indexOf("?");
		if (paramIndex != -1) {
			uri = uri.substring(0, paramIndex);
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

	public Map<String, String> getHeaderFields() {
		return headerFields;
	}

	public void setHeaderFields(Map<String, String> headerFields) {
		this.headerFields = headerFields;
	}

	public List<String> getBody() {
		return body;
	}

	public void setBody(List<String> body) {
		this.body = body;
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

}