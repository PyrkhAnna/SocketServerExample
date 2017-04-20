package com.epam.ws1.logic;

import java.util.Map;

import com.epam.ws1.Const;
import com.epam.ws1.logic.impl.DELETEAnalyzer;
import com.epam.ws1.logic.impl.GETAnalyzer;
import com.epam.ws1.logic.impl.POSTAnalyzer;
import com.epam.ws1.logic.impl.PUTAnalyzer;

public class AnalyzerFactory {
	public Analyzer createAnalyzer(String method, Map<String, String> requestParametersSet) {
		Analyzer analyzer = null;
		switch (method.toUpperCase()) {
		case Const.METHOD_DELETE:
			analyzer = new DELETEAnalyzer(requestParametersSet.get(Const.URI), requestParametersSet.get(Const.ACCEPT_TYPE));
			break;
		case Const.METHOD_GET:
			analyzer = new GETAnalyzer(requestParametersSet.get(Const.URI), requestParametersSet.get(Const.ACCEPT_TYPE));
			break;
		case Const.METHOD_POST:
			analyzer = new POSTAnalyzer(requestParametersSet.get(Const.URI), requestParametersSet.get(Const.BODY));
			break;
		case Const.METHOD_PUT:
			analyzer = new PUTAnalyzer(requestParametersSet.get(Const.URI), requestParametersSet.get(Const.BODY));
			break;
		}
		return analyzer;
	}
}
