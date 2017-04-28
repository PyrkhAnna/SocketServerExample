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

public class DELETEAnalyzer extends AbsractAnalyzer {
	private String uri;
	private String contentType;
	private Map<String, String> param;
	private int code;
	private String body;

	public DELETEAnalyzer(String uri, String contentType) {
		super();
		this.uri = uri;
		this.contentType = getContentTypeFromAcceptField(contentType);
	}

	@Override
	public String getMessage() {
		try {
			code = analyzeURL(getUrlFromUri(uri));
			// + getFileTypeFromAcceptField(contentType);
		} catch (ValidationException e) {
			System.out.println(e);
			e.printStackTrace();
			code = 400;
		}

		if (code == 200) {
			String fileUrl = Const.DEFAULT_FILES_DIR + Const.TEMP_FILE;
			body = getSource(fileUrl);
		} else {
			body = Const.ERROR_BODY;
			contentType = "text/html";
		}
		int contentLength = body.length();
		Header header = new Header(code, contentLength, contentType);
		return body != null ? header.buildHeader() + Const.NEW_LINE + body : header.buildHeader();
	}

	private String getSource(String fileUrl) {
		InputStream strm = null;
		String body = null;
		try {
			if (fileUrl != null) {
				strm = Server.class.getResourceAsStream(fileUrl);
			}
			code = (strm != null) ? 200 : 404;
			if (code == 200) {
				String filePath = Const.DEFAULT_FILES_DIR_FOR_READING_FILES + fileUrl;
				body = readFile(filePath);
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

	private int analyzeURL(String url) throws ValidationException {
		int code;
		if (url.equals("/book") || url.equals("/book/")) {
			if (param.size() == 0) {
				code = 400;
			} else {
				code = deleteBook(param.get("id"));
			}
		} else {
			code = 400;
		}
		return code;
	}

	private Book findBook(String id) throws ValidationException {
		// findBook and check
		Book book = bs.findBook(id);
		if (book != null) {
			code = 200;
			bs.parseToXML(book);
		} else {
			code = 404;
		}
		return book;
	}

	private int deleteBook(String id) throws ValidationException {
		int code = 0;
		if (findBook(id) != null) {
			code = deleteBookFromBase(id);
			updateBookBase();
		} else {
			code = 404;
		}
		return code;
	}

	private int deleteBookFromBase(String id) throws ValidationException {
		return bs.deleteBook(id) ? 200 : 500;
	}

	private void updateBookBase() {
		bs.parseToXML(bs.findAllBooks());
	}

	private String getUrlFromUri(String uri) {
		String url[] = uri.split("\\?");
		if (url.length > 0) {
			param = getParam(url);
		}
		return url[0];
	}
}
