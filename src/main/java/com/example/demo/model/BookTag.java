package com.example.demo.model;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Data

@Table(name ="tags")
public class BookTag {
	
	@Id
	@GeneratedValue
	@Column(name="tag_id")
	private Long tagId;
	
	@Column(name="name",nullable=false,unique=true)
	private String name;
	
	@ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
    private Collection<Book> books ;
	
		
}
