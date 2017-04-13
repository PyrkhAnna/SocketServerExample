package com.epam.web;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {
	private static final String DEFAULT_FILES_DIR = "/www";
	// private static final int BUFFER_SIZE = 1024;
	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream output;

	public Response(OutputStream output) {
		this.output = output;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	/**
	 * Отправляет ответ клиенту. В качестве ответа отправляется http заголовок и
	 * содержимое указанного ресурса. Если ресурс не указан, отправляется
	 * перечень доступных ресурсов.
	 * 
	 * @param url
	 *            идентификатор запрашиваемого ресурса.
	 * @return код ответа. 200 - если ресурс был найден, 404 - если нет.
	 * @throws IOException
	 */
	private int send(String url) throws IOException {
		// InputStream strm = Server.class.getResourceAsStream(url);
		// InputStream strm = Server.class.getResourceAsStream(url);
		int code = (url != null && url.equalsIgnoreCase(DEFAULT_FILES_DIR)) ? 200 : 404;
		String header = getHeader(code);
		PrintStream answer = new PrintStream(output, true, "UTF-8");
		answer.print(header);
		if (code == 200) {
			sendStaticResource(url);
			// int count = 0;
			// byte[] buffer = new byte[1024];
			// while ((count = strm.read(buffer)) != -1) {
			// out.write(buffer, 0, count);
			// }
			/// strm.close();
		}
		return code;
	}

	public void sendStaticResource(String url) throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		// OutputStream output = null;
		try {
			File file = new File(url);
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
		} finally {
			if (fis != null)
				fis.close();
		}
	}

	/**
	 * Возвращает http заголовок ответа.
	 * 
	 * @param code
	 *            код результата отправки.
	 * @return http заголовок ответа.
	 */
	@SuppressWarnings("deprecation")
	private String getHeader(int code) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("HTTP/1.1 " + code + " " + getAnswer(code) + "\n");
		buffer.append("Date: " + new Date().toGMTString() + "\n");
		buffer.append("Accept-Ranges: none\n");
		buffer.append("\n");
		return buffer.toString();
	}

	/**
	 * Возвращает комментарий к коду результата отправки.
	 * 
	 * @param code
	 *            код результата отправки.
	 * @return комментарий к коду результата отправки.
	 */
	private String getAnswer(int code) {
		switch (code) {
		case 200:
			return "OK";
		case 404:
			return "Not Found";
		default:
			return "Internal Server Error";
		}
	}

	/*public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
				fis = new FileInputStream(file);
				int ch = fis.read(bytes, 0, BUFFER_SIZE);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, BUFFER_SIZE);
				}
			} else {
				// file not found
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
						+ "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
		} finally {
			if (fis != null)
				fis.close();
		}
	}*/
}