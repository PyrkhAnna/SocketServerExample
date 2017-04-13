package com.epam.web.entity;

import java.util.List;

import com.epam.web.parsers.BooksJaxBParser;

public class BooksManager {

    BooksJaxBParser parser = new BooksJaxBParser();
    List<Book> booksList = parser.unmarshallList();

    public Book getBook(String author) {
    	Book book = new Book();
        for (Book u : booksList) {
            if (u.getAuthor().equals(author)) {
            	book = u;
            }
        }
        return book;
    }
    
    public Book getBook(Integer id) {
		Book book = new Book();
		for (Book u : booksList) {
			if (u.getId()==id) {
				book = u;
			}
		}
		return book;
	}
}
