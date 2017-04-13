package com.epam.web.entity;

import java.util.List;

public class BooksManager {

    BooksJaxBParser parser = new BooksJaxBParser();
    List<Book> usersList = parser.unmarshallList();

    public Book getUser(String id) {
    	Book book = new Book();
        for (Book u : usersList) {
            if (u.getId().equals(id)) {
            	book = u;
            }
        }
        return book;
    }
}
