package com.epam.ws1.logic.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
			if (code == 200) {
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

	private String readFile(String filePath) throws IOException {
		byte[] bytes = new byte[Const.BUFFER_SIZE];
		FileInputStream fis = null;
		StringBuilder body = new StringBuilder();
		File file = new File(System.getProperty("user.dir") + filePath);
		if (file.exists()) {
			fis = new FileInputStream(file);
			int ch = fis.read(bytes, 0, Const.BUFFER_SIZE);
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

	private String analyzeURL(String url) throws ValidationException {
		// return file if exist, check accept before
	
		if (url.equals("/")) {
			code = 404;
		} else {
			if (param.size() == 0) {
				if (url.equals("/book")||url.equals("/book/")) {
					code = 200;
					return url = Const.DEFAULT_FILES_DIR+Const.FILE_BASE;
				} else {
					code = 200;
					url=Const.DEFAULT_FILES_DIR+url;
				}
			} else {
				// findBook
				BooksService bs = new BooksService();
				Book book = bs.findBook(param.get("id"));
				if (book != null) {
					code = 200;
					bs.parseToXML(book);
					url = Const.DEFAULT_FILES_DIR+ Const.TEMP_FILE;
				}
			}
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
			getParam(url);
		}
		return url[0];
	}

	private void getParam(String[] url) {
		param = new HashMap<String, String>();
		//String[] str ;
		for (int i = 1; i < url.length; i++) {   //////?????
			String[] str = url[i].split("=");
			param.put(str[0], str[1]);
		}
	}
}
