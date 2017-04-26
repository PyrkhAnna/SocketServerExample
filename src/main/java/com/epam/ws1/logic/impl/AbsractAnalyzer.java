package com.epam.ws1.logic.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.epam.ws1.Const;
import com.epam.ws1.logic.Analyzer;
import com.epam.ws1.logic.BooksService;

public abstract class AbsractAnalyzer implements Analyzer {
	public BooksService bs = new BooksService();
	
	
	public Map<String, String> getParam(String[] stringParam) {
		Map<String, String> paramSet = new HashMap<String, String>();
		for (int i = 0; i < stringParam.length; i++) {   
			String[] str = stringParam[i].split("=");
			if (str.length>1) {
			 paramSet.put(str[0], str[1]);
			}
		}
		return paramSet;
	}
	public String readFile(String filePath) throws IOException {
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
}
