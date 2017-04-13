package com.epam.web.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "books")
public class Books implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Book> bookList = new ArrayList<Book>();

	public Books() {
		bookList = new ArrayList<Book>();
	}

	public Books(Collection<? extends Book> c) {
		bookList = new ArrayList<Book>(c);
	}

	@XmlElement(name = "book")
	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

}
