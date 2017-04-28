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
	private Map<String, String> requestParametersSet;

	public Response(OutputStream output) {
		this.output = output;
		message = null;
		requestParametersSet = new HashMap<String, String>();
	}

	public void sendMessage() {
		String method = requestParametersSet.get(Const.METHOD_TYPE);
		AnalyzerFactory factory = new AnalyzerFactory();
		Analyzer analyzer = factory.createAnalyzer(method,requestParametersSet);
		message = analyzer.getMessage();
		try {
			if (message != null) {
				output.write(message.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setRequest(Request request) {
		requestParametersSet = request.getParametersSet();
	}
	
}