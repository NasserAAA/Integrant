package com.example.demo.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;



@Entity
@Data 
@Table(name="books")
public class Book {

	@Id
	@GeneratedValue
	@Column(name="book_id")
	private Long bookId;
	
	@Column(name="title",nullable = false)
	private String title;
	
	@Column(name="uniquetitle",nullable=false, unique=true)
	private String uniquetitle ;
	
	@Column(name="instock")
	private Boolean instock;
	
	@Column(name="stock")
	private int stock;
	
	@ManyToOne(fetch = FetchType.LAZY,targetEntity = Author.class)
	@JoinColumn(name="author")
	private Author author;
	
	@ManyToMany(fetch = FetchType.LAZY,targetEntity = BookTag.class)
    @JoinTable(name = "books_tags",
    joinColumns = @JoinColumn(name = "book_id", nullable = false,updatable = false),
    inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false,updatable = false),
    foreignKey = @ForeignKey(value= ConstraintMode.CONSTRAINT),
    inverseForeignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
	private Collection<BookTag> tags;
	
	public Book() {
		this.instock =false;
		this.stock=0;
	}
	
	public void setUniqueTitle() {
	this.uniquetitle = this.title+" Book By "+author.getName();
}
}