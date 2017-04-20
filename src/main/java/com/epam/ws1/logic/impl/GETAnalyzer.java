package com.epam.ws1.logic.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.xml.bind.ValidationException;
import com.epam.ws1.Const;
import com.epam.ws1.Server;
import com.epam.ws1.entity.Book;
import com.epam.ws1.logic.Header;

public class GETAnalyzer extends AbsractAnalyzer {
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
		String fileUrl = null;
		int contentLength = 0;
		try {
			fileUrl = analyzeURL(getUrlFromUri(uri));
			// + getFileTypeFromAcceptField(contentType);
		} catch (ValidationException e) {
			System.out.println(e);
			e.printStackTrace();
			code = 500;
		}
		if (code == 200) {
			body = getSource(fileUrl);
			contentLength = body.length();
		} else {
			body = Const.ERROR_BODY;
			contentType = "text/html";
			contentLength = body.length();
		}
		Header header = new Header(code, contentLength, contentType);
		return body != null ? header.buildHeader() +Const.NEW_LINE+ body : header.buildHeader();
	}

	private String getSource(String fileUrl) {
		InputStream strm = null;
		String body = null;
		try {
			if (fileUrl != null) {
				strm = Server.class.getResourceAsStream(fileUrl);
			}
			code = (strm != null) ? 200 : 404;
			if (code==200) {
				String filePath= Const.DEFAULT_FILES_DIR_FOR_READING_FILES+fileUrl;
				body =readFile(filePath);
				strm.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			code = 404;
		} catch (IOException e) {
			e.printStackTrace();
			code = 500;
		}
		return body;
	}

	private String analyzeURL(String url) throws ValidationException {
		// return file if exist, check accept before
		if (url.equals("/book")||url.equals("/book/")) {
			if (param.size() == 0) {
				code = 200;
				url = Const.DEFAULT_FILES_DIR+Const.FILE_BASE;
			}  else {
				url = findBookURL();
			}
		}  else {
			code = 400;
		}
		return url;
	}
	private String findBookURL() throws ValidationException {
		String url = null;
		Book book = bs.findBook(param.get("id"));
		if (book != null) {
			code = 200;
			bs.parseToXML(book);
			url = Const.DEFAULT_FILES_DIR+ Const.TEMP_FILE;
		} else {
			code = 500;
			url=Const.DEFAULT_FILES_DIR+Const.ERROR_PATH_500;
		}
		return url;
	}
/*
	private String getFileTypeFromAcceptField(String acceptField) {
		int index = acceptField.indexOf('/');
		if (index != -1) {
			return acceptField.substring(index + 1, acceptField.length() - 1);
		}
		return null;
	}*/

	private String getUrlFromUri(String uri) {
		String url[] = uri.split("\\?");
		if (url.length > 0) {
			param = getParam(url);
		}
		return url[0];
	}
}
