package com.epam.ws1.logic;

import java.util.List;
import javax.xml.bind.ValidationException;
import com.epam.ws1.Const;
import com.epam.ws1.entity.Book;
import com.epam.ws1.entity.Books;
import com.epam.ws1.parser.BooksJaxBParser;

public class BooksService {

	private BooksJaxBParser parser;
	private Books books;
	
	public BooksService() {
		super();
		parser = new BooksJaxBParser();
		books = parser.unmarshallList(Const.BASE_FILE_PATH);
	}

	public Book findBook(String id) throws ValidationException {
		if (id == null) {
			throw new ValidationException("Invalid book id");
		}
		return books.getBook(id);
	}

	public List<Book> findAllBooks() {
		return books.getBookList();
	}

	public boolean createBook(Book book) throws ValidationException { // put
		if (book == null) {
			throw new ValidationException("Book object is null");
		}
		if (books.getBook(book.getId()) == null) {
			throw new ValidationException("Book object already exist");
		}
		return books.addBook(book);
	}

	public boolean deleteBook(String id) throws ValidationException {
		if (id == null) {
			throw new ValidationException("Invalid book id");
		}
		return books.deleteBook(id);
	}
	public void parseToXML(Books books) {
		parser.marshall(books);
	}
	public void parseToXML(Book book) {
		parser.marshall(books);
	}
}
