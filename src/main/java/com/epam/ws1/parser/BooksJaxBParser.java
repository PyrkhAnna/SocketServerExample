package com.epam.ws1.parser;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.epam.ws1.Const;
import com.epam.ws1.entity.Book;
import com.epam.ws1.entity.Books;

public class BooksJaxBParser {
	public Books unmarshallList(String filePath) { // read from file
		Books books = null;
		try {
			File file = new File(filePath);
			JAXBContext context = JAXBContext.newInstance(Books.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			books = (Books) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
		return books;
	}

	public void marshall(Books books) { /// write into file
		try {
			File file = new File(Const.BASE_FILE_PATH);
			JAXBContext context = JAXBContext.newInstance(Books.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(books, file);
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
	}

	public Book unmarshallBook() { // read from stream
		Book book = null;
		try {
			File file = new File(Const.TEMP_FILE_PATH);
			JAXBContext context = JAXBContext.newInstance(Books.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			book = (Book) unmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
		return book;
	}

	public void marshall(Book book) { /// write into file
		try {
			File file = new File(Const.TEMP_FILE_PATH);
			JAXBContext context = JAXBContext.newInstance(Books.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(book, file);
		} catch (JAXBException e) {
			System.out.println(e.getMessage());
		}
	}
}
