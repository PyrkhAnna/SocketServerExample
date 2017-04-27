package com.epam.ws1.logic;

import com.epam.ws1.Const;
import com.epam.ws1.Request;
import com.epam.ws1.logic.impl.DELETEAnalyzer;
import com.epam.ws1.logic.impl.GETAnalyzer;
import com.epam.ws1.logic.impl.POSTAnalyzer;
import com.epam.ws1.logic.impl.PUTAnalyzer;

public class AnalyzerFactory {
	public Analyzer createAnalyzer(Request request) {
		Analyzer analyzer = null;
		switch (request.getMethodType().toUpperCase()) {
		case Const.METHOD_DELETE:
			analyzer = new DELETEAnalyzer(request.getUri(), request.getAcceptType());
			break;
		case Const.METHOD_GET:
			analyzer = new GETAnalyzer(request.getUri(), request.getAcceptType());
			break;
		case Const.METHOD_POST:
			analyzer = new POSTAnalyzer(request.getUri(), request.getBody());
			break;
		case Const.METHOD_PUT:
			analyzer = new PUTAnalyzer(request.getUri(), request.getBody());
			break;
		}
		return analyzer;
	}
}
