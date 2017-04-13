package com.epam.web;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;

public class Request {

	private InputStream input;
	private String uri;
	private List<String> header;
	private List<String> body;
	private String contentType;
	private String methodType;

	private static final String DEFAULT_FILES_DIR = "/www";

	public Request(InputStream input) {
		this.input = input;
		header = new ArrayList<String>();
		body = new ArrayList<String>();
	}

	public void parse() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));

		// Read a set of characters from the socket
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
		String[] str = request.toString().split("\r\n");

		i = 0;
		boolean flag = false;
		while (i < str.length) {
			if (str[i].isEmpty()) {
				i++;
				flag = true;
			}
			if (!flag) {
				header.add(str[i]);
			} else {
				body.add(str[i]);
			}
			i++;
		}
		System.out.println(header.toString());
		// System.out.println(body.toString());

		uri = getURIFromHeader(str[0]);
		contentType = getContentTypeFromHeader();
		methodType = getMethod(header.get(0));
		System.out.println(uri);
		System.out.println(contentType);
		System.out.println(methodType);
	}

	private String getURIFromHeader(String header) {
		int from = header.indexOf(" ") + 1;
		int to = header.indexOf(" ", from);
		String uri = header.substring(from, to);
		int paramIndex = uri.indexOf("?");
		if (paramIndex != -1) {
			uri = uri.substring(0, paramIndex);
		}
		return uri;
	}

	private String getContentTypeFromHeader() {
		int from;
		String value = null;
		for (String s : header) {
			if (s.startsWith("Content-Type:")) {
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

	public String getUri() {
		return uri;
	}

}