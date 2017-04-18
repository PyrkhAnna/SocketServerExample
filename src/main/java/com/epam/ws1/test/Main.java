package com.epam.ws1.test;

import java.io.File;

import com.epam.ws1.Const;
import com.epam.ws1.entity.Book;
import com.epam.ws1.entity.Books;
import com.epam.ws1.parser.BooksJaxBParser;

public class Main {

	public static void main(String[] args) {
		BooksJaxBParser parser= new BooksJaxBParser();
		Book book = new Book(2, "Ben");
		parser.marshall(book);
		//File file = new File(Const.TEMP_FILE_PATH);
		Book book1=parser.unmarshallBook();
		System.out.println(book1);
		
		Books books= parser.unmarshallList(Const.BASE_FILE_PATH);
		System.out.println(books.getSize());
		System.out.println(books);
		System.out.println(books.getBook(2));
		
		/*
		
		BooksService bs = new BooksService();
		Book book = null;
		try {
			book = bs.findBook("2");
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (book != null) {
			
			bs.parseToXML(book);
			
		}
*/
	}

}
