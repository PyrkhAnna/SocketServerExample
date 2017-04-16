package com.epam.ws1.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L; 
    private int id;
    private String author;
    
    public Book() {
		super();
	}
    
	public Book(int id, String author) {
		super();
		this.id = id;
		this.author = author;
	}
	
	public int getId() {
		return id;
	}
	@XmlElement//(name = "id")
	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}
	@XmlElement//(name = "author")
	public void setAuthor(String author) {
		this.author = author;
	}

}
