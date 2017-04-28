package com.epam.ws1;

public class Const {
	public static final int DEFAULT_PORT = 8080;
	public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	public static final String BASE_FILE_PATH = System.getProperty("user.dir")
			+ Const.DEFAULT_FILES_DIR_FOR_READING_FILES + Const.DEFAULT_FILES_DIR + Const.FILE_BASE;
	public static final String TEMP_FILE_PATH = System.getProperty("user.dir")
			+ Const.DEFAULT_FILES_DIR_FOR_READING_FILES +Const.DEFAULT_FILES_DIR + "/2.xml";
	public static final String BASE_FILE_PATH_JSON = System.getProperty("user.dir")
			+ Const.DEFAULT_FILES_DIR_FOR_READING_FILES + Const.DEFAULT_FILES_DIR + Const.FILE_BASE_JSON;
	public static final String DEFAULT_FILES_DIR_FOR_READING_FILES = "/src/main/resources";
	public static final String DEFAULT_FILES_DIR = "/www";
	public static final String FILE_BASE_JSON = "/book/booksbase.json";
	public static final String FILE_BASE = "/book/booksbase.xml";
	public static final String TEMP_FILE = "/2.xml";
	public static final String ERROR_BODY = "<!DOCTYPE HTML> <html><head><title>Error</title></head><body><h1>Error</h1></body></html>";
	public static final String ERROR_PATH_500 = "/500.html";
	public static final String ERROR_PATH_400 = "/400.html";
	public static final int BUFFER_SIZE = 1024;
	public static final String METHOD_TYPE = "Method";
	public static final String URI = "URI";
	public static final String BODY = "Body";
	public static final String METHOD_GET = "GET";
	public static final String METHOD_PUT = "PUT";
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_POST = "POST";
	public static final String PROTOCOL = "HTTP/1.1";
	public static final String LOCATION = "Location: ";
	public static final String DATE = "Date: ";
	public static final String SERVER = "Server: http://localhost:"+DEFAULT_PORT+" (Win64)";
	// "Server: " + System.getProperty("Server.host")+
	// System.getProperty("Server.port") + " (Win32)";

	public static final String CONTENT_TYPE = "Content-Type: ";
	public static final String CONTENT_LENGTH = "Content-Length: ";
	public static final String ACCEPT_TYPE = "Accept: ";
	public static final String CONNECTION = "Connection: Closed";
	public static final String NEW_LINE = "\r\n";
	public static final String ID = "id";
	public static final String AUTHOR = "author";

}
