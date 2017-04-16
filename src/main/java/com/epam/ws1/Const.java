package com.epam.ws1;

public class Const {
	public static final int DEFAULT_PORT = 7777;
	public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	public static final String BASE_FILE_PATH = Const.DEFAULT_FILES_DIR
			+ Const.FILE_BASE;
	public static final String TEMP_FILE_PATH = Const.DEFAULT_FILES_DIR
			+ "/1.xml";
	public static final String DEFAULT_FILES_DIR = "/www"; 
	public static final String DEFAULT_FILES_DIR_FOR_READING_FILES = "/src/main/resources"; 
	//\src\main\resources\www\
	public static final String FILE_BASE = "/book/booksbase.xml";
	public static final int BUFFER_SIZE = 1024;
	public static final String METHOD_TYPE = "Method";
	public static final String URI = "URI";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_POST = "POST";
	public static final String PROTOCOL = "HTTP/1.1";
	public static final String LOCATION = "Location: ";
	public static final String DATE = "Date: ";
	public static final String SERVER = "Server: http://localhost:7777 (Win64)";
	//"Server: " + System.getProperty("Server.host")+ System.getProperty("Server.port") + " (Win32)";

	public static final String CONTENT_TYPE = "Content-Type: ";
	public static final String CONTENT_LENGTH = "Content-Length: ";
	public static final String ACCEPT_TYPE = "Accept: ";
	public static final String CONNECTION = "Connection: Closed";
	public static final String NEW_LINE = "\r\n";

}
