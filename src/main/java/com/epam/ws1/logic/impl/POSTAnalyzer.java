package com.epam.ws1.logic.impl;

import java.util.Map;
import javax.xml.bind.ValidationException;
import com.epam.ws1.Const;
import com.epam.ws1.logic.Header;

public class POSTAnalyzer extends AbsractAnalyzer {
	private String uri;
	private Map<String, String> paramSet;
	private String requestBody;

	public POSTAnalyzer(String uri, String requestBody) {
		this.uri = uri;
		this.requestBody = requestBody;
	}

	@Override
	public String getMessage() {
		int code = analyzeURL(uri);
		if (code == 200) {
			code = getParamSet();
			try {
				code = bs.updateBook(paramSet.get(Const.ID), paramSet.get(Const.AUTHOR)) ? 200 : 500;//// ?????

			} catch (ValidationException e) {
				e.printStackTrace();
				code = 400;
			}
		}
		Header header = new Header(code);
		return header.buildHeader();
	}

	private int getParamSet() {
		int code;
		if (requestBody != null) {
			paramSet = getParamFromBody(requestBody);
			code = 200;
		} else {
			code = 400;
		}
		return code;
	}

	private int analyzeURL(String url) {
		return url.equals("/book/bookbase.xml") ? 200 : 400;
	}

	private Map<String, String> getParamFromBody(String body) {
		String stringParam[] = body.split(";");
		return stringParam.length > 0 ? getParam(stringParam) : null;
	}
}
