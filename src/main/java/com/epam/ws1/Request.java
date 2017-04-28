package com.epam.ws1;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public class Request {
	
	private InputStream input;
	//private StringBuffer body;
	private Map<String, String> paramSet;

	public Request(InputStream input) {
		this.input = input;
		paramSet = new HashMap<String, String>();
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
		
		paramSet.put(Const.CONTENT_TYPE, getFromHeader(header, Const.CONTENT_TYPE));
		paramSet.put(Const.CONTENT_LENGTH, getFromHeader(header, Const.CONTENT_LENGTH));
		paramSet.put(Const.ACCEPT_TYPE, getFromHeader(header, Const.ACCEPT_TYPE));
		paramSet.put(Const.URI, getURIFromHeader(str[0]));
		paramSet.put(Const.METHOD_TYPE, getMethod(header.get(0).trim()));
		paramSet.put(Const.BODY, body.toString());
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
		
	public String getURL() {
		return paramSet.get(Const.URI);
	}
}