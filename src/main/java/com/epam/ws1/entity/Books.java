package com.epam.ws1.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "books")
@XmlSeeAlso(Book.class)
public class Books implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Book> bookList = new ArrayList<Book>();

	public Books() {
		
	}
	public Books(Collection<? extends Book> c) {
		bookList = new ArrayList<Book>(c);
	}
		
	public boolean addBook(Book book) {
		return bookList.add(book);
	}
	public Book getBook(String id) {
		return bookList.stream().filter(t -> t.getId()== Integer.parseInt(id)).findFirst().orElse(null);
	}
	public Book getBook(int id) {
		return bookList.stream().filter(t -> t.getId()== id).findFirst().orElse(null);
	}
	public int getSize() {
		return bookList.size();
	}
	public boolean deleteBook(String id) {
		return bookList.remove(getBook(id));
	}
	public List<Book> getBookList() {
		return bookList;
	}
	@XmlElement(name = "book")
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
}
