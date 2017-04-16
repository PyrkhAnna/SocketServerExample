package com.epam.ws1.logic;

import java.util.Map;

import com.epam.ws1.Const;
import com.epam.ws1.logic.impl.GETAnalyzer;

public class AnalyzerFactory {
	public Analyzer createAnalyzer(String method, Map<String, String> requestHeaderFields) {
		Analyzer analyzer = null;
		switch (method.toUpperCase()) {
		case Const.METHOD_DELETE:
			// analyzer = new Analyzer(requestHeaderFields);
			break;
		case Const.METHOD_GET:
			analyzer = new GETAnalyzer(requestHeaderFields.get(Const.URI), requestHeaderFields.get(Const.ACCEPT_TYPE));
			break;
		case Const.METHOD_POST:
			// analyzer = new Analyzer(requestHeaderFields);
			break;
		case Const.METHOD_PUT:
			// analyzer = new Analyzer(requestHeaderFields);
			break;
		}
		return analyzer;
	}
}
