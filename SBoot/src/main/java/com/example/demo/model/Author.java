package com.example.demo.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="authors")
public class Author {
	
	@Id
	@GeneratedValue
	@Column(name="author_id")
	private Long authorId;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@Column(name="email",nullable=false,unique=true)
	private String email;
	
	@OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    private Collection<Book> books;
	
}
