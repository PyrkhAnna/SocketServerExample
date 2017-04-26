package com.epam.ws1.logic.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.epam.ws1.Const;
import com.epam.ws1.logic.Header;

public class PUTAnalyzer extends AbsractAnalyzer {
	private String uri;
	private int code;
	
	private String requestBody;

	public PUTAnalyzer(String uri, String requestBody) {
		this.uri = uri;
		this.requestBody = requestBody;
	}

	@Override
	public String getMessage() {
		String location = null;
		String fileName = null;
		try {
			if (ifFileExist(uri)) {
				fileName = createNewFileName();
				if (createFile(System.getProperty("user.dir") + Const.DEFAULT_FILES_DIR_FOR_READING_FILES +Const.DEFAULT_FILES_DIR+ fileName)) {
					code = 301;
				} else {
					code = 500;
				}
			} else {
				fileName = uri;
				code = 201;
			}
			if (code == 301 || code == 201) {
				createResource(System.getProperty("user.dir") + Const.DEFAULT_FILES_DIR_FOR_READING_FILES +Const.DEFAULT_FILES_DIR+ fileName);
			}
		} catch (IOException e) {
			code = 500;
			e.printStackTrace();
		}
		location = "http://localhost:" + Const.DEFAULT_PORT + "/" + fileName;
		Header header = new Header(code, location);
		return header.buildHeader();
	}

	private void createResource(String filePath) {
		try (FileWriter writer = new FileWriter(filePath, false)) {
			writer.write(requestBody);
			writer.flush();
		} catch (IOException e) {
			code = 500;
			e.printStackTrace();
		}
	}

	private boolean createFile(String filePath) throws IOException {
		return new File(filePath).createNewFile();
	}

	private boolean ifFileExist(String uri) throws IOException {
		return new File(System.getProperty("user.dir") + Const.DEFAULT_FILES_DIR_FOR_READING_FILES +Const.DEFAULT_FILES_DIR+ uri).exists();
	}

	private String createNewFileName() throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return sdf.format(System.currentTimeMillis()) + ".xml";
	}
}
