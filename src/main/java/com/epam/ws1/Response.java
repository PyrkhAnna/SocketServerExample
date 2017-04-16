package com.epam.ws1;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import com.epam.ws1.logic.Analyzer;
import com.epam.ws1.logic.AnalyzerFactory;
import java.io.IOException;

public class Response {
	
	private OutputStream output;
	private String message;
	private Map<String, String> requestHeaderFields;

	public Response(OutputStream output) {
		this.output = output;
		message = null;
		requestHeaderFields = new HashMap<String, String>();
	}

	public void sendMessage() {
		String method = requestHeaderFields.get(Const.METHOD_TYPE);
		AnalyzerFactory factory = new AnalyzerFactory();
		Analyzer analyzer = factory.createAnalyzer(method,requestHeaderFields);
		message = analyzer.getMessage();
		try {
			if (message != null) {
				output.write(message.getBytes());
				//message = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setRequest(Request request) {
		requestHeaderFields = request.getHeaderFields();
	}
	
}