package com.epam.web.entity;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class BooksJaxBParser {
	// private static final String FILE_PATH = "src\\main\\resources\\usersbase.xml";
	private static final String FILE_PATH = "www\\book\\booksbase.xml";
    List<Book> booksList;
   
    public List<Book> unmarshallList() {
        try {
            JAXBContext jc = JAXBContext.newInstance(Books.class);
            Unmarshaller um = jc.createUnmarshaller();
            Books books = (Books) um.unmarshal(new File(FILE_PATH));
            booksList = books.getBookList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return booksList;
    }
    
    public void marshallList( List<Book> booksList) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Books.class);
            Marshaller um = jc.createMarshaller();
            um.marshal(booksList, new File(FILE_PATH));;
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
      
    }
}
