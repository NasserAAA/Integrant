package com.example.demo.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="authors")
public class Author extends UserRecord {
	
	
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
