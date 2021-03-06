package com.example.demo.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.dto.AuthorDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookTagRepository;

@ExtendWith(SpringExtension.class)
public class AuthorMapperTest {
	
	@TestConfiguration
    static class AuthorMapperTestContextConfiguration {
  
    }
	
	@MockBean
	private BookRepository bookRepository;
	
	@MockBean
	private BookTagRepository bookTagRepository;
	
	@MockBean
	private AuthorRepository authorRepository;
	
	private AuthorMapper mapper=Mappers.getMapper(AuthorMapper.class);
	
	@BeforeEach
    public void setUp() {
		Book book = new Book();
        book.setTitle("Test Title");
        book.setUniquetitle("Test Title Book By Unknown Author");
        Author author = new Author();
		author.setName("Unknown Author");
		author.setEmail("UA@gmail.com");
		authorRepository.save(author);
		book.setAuthor(author);
		BookTag bookTag = new BookTag("Test");
		bookTagRepository.save(bookTag);
		book.setTags(new ArrayList<>(Arrays.asList(bookTag)));
		Mockito.when(authorRepository.findByName(author.getName()))
        .thenReturn(author);
    }
	
	 @Test
	  public void givenSourceToDestination_whenMaps_thenCorrect() {
		 Book bookSource = new Book();
		 bookSource.setBookId((long) 10);
		 bookSource.setInstock(true);
		 bookSource.setStock(100);
		 bookSource.setTitle("Book Test");
		 bookSource.setUniquetitle("Book Test Book By Author Test");
		 BookTag bookTag = new BookTag("Tag Test");
		 bookTagRepository.save(bookTag);
		 Author author = new Author();
		 author.setName("Unknown Author");
		 author.setEmail("UA@gmail.com");
		 authorRepository.save(author);
		 bookSource.setTags(new ArrayList<>(Arrays.asList(bookTag)));
		 bookSource.setAuthor(author);
		 AuthorDTO destination  = mapper.AuthorToDTO(author, authorRepository);
		 assertEquals(author.getName(), destination.getName());
	 }
	 
	 @Test
	 public void givenDestinationToSource_whenMaps_thenCorrect() {
		  AuthorDTO destination = new AuthorDTO();
		  destination.setName("Unknown Author");
		  destination.setBooks(new ArrayList<>(Arrays.asList("Test Title")));
	      Author source = mapper.DTOToAuthor(destination, authorRepository);
	      assertEquals(destination.getName(), source.getName());
	}
	

}
