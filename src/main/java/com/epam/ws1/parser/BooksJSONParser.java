package com.epam.ws1.parser;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.epam.ws1.Const;
import com.epam.ws1.entity.Book;
import com.epam.ws1.entity.Books;

public class BooksJSONParser {

	public void parse(Books books) {
		File file = new File(Const.BASE_FILE_PATH_JSON);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(file, books);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void parse(Book book) {
		File file = new File(Const.TEMP_FILE_PATH);
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(file, book);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Books convertToBooks(String body) {
		Books books = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			books = mapper.readValue(body, Books.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return books;
	}

	public Book convertToBook(String body) {
		Book book = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			book = mapper.readValue(body, Book.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return book;
	}
}
