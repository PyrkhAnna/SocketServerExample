package com.epam.ws1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class Request {
	
	private InputStream input;
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
		headerFields.put(Const.CONTENT_TYPE, getFromHeader(header, Const.CONTENT_TYPE));
		headerFields.put(Const.CONTENT_LENGTH, getFromHeader(header, Const.CONTENT_LENGTH));
		headerFields.put(Const.ACCEPT_TYPE, getFromHeader(header, Const.ACCEPT_TYPE));
		headerFields.put(Const.URI, getURIFromHeader(str[0]));
		headerFields.put(Const.METHOD_TYPE, getMethod(header.get(0).trim()));
		System.out.println(headerFields.get(Const.URI));
		System.out.println(headerFields.get(Const.METHOD_TYPE));
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
			uri = header.substring(from, to);
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
	
	public List<String> getBody() {
		return body;
	}
	public void setBody(List<String> body) {
		this.body = body;
	}
	public String getURL() {
		return headerFields.get(Const.URI);
	}
}