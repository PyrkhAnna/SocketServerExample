package com.epam.ws1.logic;

import java.util.Date;
import com.epam.ws1.Const;

public class Header {
	private int code;
	private String location;
	private int contentLength;
	private String contentType;

	public Header(int code, String location, int contentLength, String contentType) {
		super();
		this.code = code;
		this.location = location;
		this.contentLength = contentLength;
		this.contentType = contentType;
	}
	public Header(int code, int contentLength, String contentType) {
		super();
		this.code = code;
		this.contentLength = contentLength;
		this.contentType = contentType;
	}

	public String buildHeader() {
		return buildStatusLine(code) + buildHeaderFields(location, contentLength, contentType);
	}

	private String buildHeaderFields(String location, int contentLength, String contentType) {
		String s = null;
		if (location != null) {
			s = Const.LOCATION + location + Const.NEW_LINE;
		}
		s = s + Const.DATE + new Date().toString() + Const.NEW_LINE 
				+ Const.SERVER + Const.NEW_LINE;
		if (contentLength != 0 ) {
			s = s + Const.CONTENT_LENGTH + contentLength + Const.NEW_LINE;
		}
				
		s = s 	+ Const.CONTENT_TYPE + contentType + Const.NEW_LINE 
				+ Const.CONNECTION + Const.NEW_LINE;
		return s;
	}

	private String buildStatusLine(int code) {
		return Const.PROTOCOL + code + getPhrase(code) + Const.NEW_LINE;
	}

	private String getPhrase(int code) {
		switch (code) {
		case 200:
			return "OK";
		case 201:
			return "Created";
		case 400:
			return "Bad Request";
		case 404:
			return "Not Found";
		case 406:
			return "Not Acceptable";
		case 204:
			return "No Content";
		case 501:
			return "Not Implemented";
		default:
			return "Internal Server Error";
		}
	}
}
