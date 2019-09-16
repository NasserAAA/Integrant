package com.example.demo.model;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="authors")
public class Author extends UserRecord {
	
	 
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="user_id") 
	private Long userId;
	
	@Column(name="num_books",nullable=false)
	private int numBooks;
	
	@OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private Collection<Book> books;

	public Author() {
		
		if(this.books!=null)
			this.numBooks=this.books.size();
		else
			this.numBooks=0;
	}
	
}
