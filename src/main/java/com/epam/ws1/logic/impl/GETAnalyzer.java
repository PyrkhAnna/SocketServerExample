package com.epam.ws1.logic.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.ValidationException;
import com.epam.ws1.Const;
import com.epam.ws1.Server;
import com.epam.ws1.entity.Book;
import com.epam.ws1.logic.Analyzer;
import com.epam.ws1.logic.BooksService;
import com.epam.ws1.logic.Header;

public class GETAnalyzer implements Analyzer {
	private String uri;
	private String contentType;
	private Map<String, String> param;
	private int code;
	private String body;

	public GETAnalyzer(String uri, String contentType) {
		this.uri = uri;
		this.contentType = contentType;
	}

	@Override
	public String getMessage() {
		String filePath = null;
		int contentLength = 0;
		try {
			filePath = analyzeURL(getUrlFromUri(uri)) 
					+ getFileTypeFromAcceptField(contentType);
		} catch (ValidationException e) {
			System.out.println(e);
			e.printStackTrace();
			code=500;
		}
		if (code == 200) {
			contentLength = getSource(filePath); 
		}
		Header header= new Header(code, contentLength, contentType);
		return header.buildHeader()+body;
	}

	private int getSource(String filePath) {
		InputStream strm = null;
		int count = 0;
		try {
			if (filePath != null) {
				strm = Server.class.getResourceAsStream(filePath);
			}
			code = (strm != null) ? 200 : 404;
			if (code == 200) {
				byte[] buffer = new byte[1024];
				StringBuffer tempbody = new StringBuffer(2048);
				while ((count = strm.read(buffer)) != -1) {
					tempbody.append((char) buffer[count]);
				}
				strm.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			code = 500;
		}
		return count;
	}

	private String analyzeURL(String url) throws ValidationException {
		// return file if exist, check accept before
		String filePath = null;

		if (url.equals("/")) {
			code = 404;
		}
		if (param.size() == 0) {
			if (url.equals("/book")) {
				filePath = Const.BASE_FILE_PATH;
				code = 200;
			}
		} else {
			// findBook
			BooksService bs = new BooksService();
			Book book = bs.findBook(param.get("id"));
			if (book != null) {
				code = 200;
				bs.parseToXML(book);
				filePath = Const.TEMP_FILE_PATH;
			}
		}
		return filePath;
	}

	private String getFileTypeFromAcceptField(String acceptField) {
		int index = acceptField.indexOf('/');
		if (index != -1) {
			return acceptField.substring(index + 1, acceptField.length() - 1);
		}
		return null;
	}

	private String getUrlFromUri(String uri) {
		String url[] = uri.split("\\?");
		if (url.length > 0) {
			getParam(url);
		}
		return url[0];
	}

	private void getParam(String[] url) {
		param = new HashMap<String, String>();
		String[] str;
		for (int i = 1; i > url.length; i++) {
			str = url[i].split("=");
			param.put(str[0], str[1]);
		}
	}
}
