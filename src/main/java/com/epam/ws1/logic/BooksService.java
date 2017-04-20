package com.epam.ws1.logic;

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

	public Books findAllBooks() {
		return books;
	}

	/*public boolean addBook(Book book) throws ValidationException { // put
		if (book == null) {
			throw new ValidationException("Book object is null");
		}
		if (books.getBook(book.getId()) != null) {
			throw new ValidationException("Book object already exist");
		}
		return books.addBook(book);
	}*/
	public boolean createBook(String id, String author) throws ValidationException { // put
		if (author == null) {
			throw new ValidationException("Author field is null");
		}
		if (books.getBook(id) != null) {
			throw new ValidationException("Object with same Book Id already exist");
		}
		return books.addBook(new Book(Integer.parseInt(id), author));
	}
	
	public boolean updateBook(String id, String author) throws ValidationException { // post
		if (id==null||author == null) {
			throw new ValidationException("Value fields is incorrect");
		} 
		boolean flag= false;
		Book book = books.getBook(id);
		if (book!= null) {
			book.setAuthor(author);
			flag = true;
		} else {
			 flag = createBook(id, author);
		}
		if (flag==true) {
			parseToXML(books);
		}
		return flag;
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
		parser.marshall(book);
	}
}
