package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.example.demo.TestConfiguration;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookTagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT )
@ContextConfiguration(
		  classes = { TestConfiguration.class })
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorControllerTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private BookTagRepository bookTagRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	@BeforeEach
	public void setup()
	{	
		  mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		  
	}
	
	@Test
	@Order(1)
	public void whenGetAuthors_thenOk() throws Exception
    {	
    	Author author = new Author(); 
    	author.setName("Dummy Author");
    	author.setEmail("DA@gmail.com");
    	authorRepository.save(author);
    	Author author2 = new Author(); 
    	author2.setName("Unknown");
    	author2.setEmail("U@gmail.com");
    	authorRepository.save(author2);
    	mockMvc.perform(get("/api/authors")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].name", is("Dummy Author")));
    }
	
	@Test
	@Order(2)
	public void whenFindAuthorByName_thenOk() throws Exception
	{
		mockMvc.perform(get("/api/authors/email/{email}", "U@gmail.com")
		          .contentType(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("name", is("Unknown")));
		
	}
	
	@Test
	@Order(3)
	public void whenFindAuthorByEmail_thenOk() throws Exception
	{
		mockMvc.perform(get("/api/authors/{name}", "Dummy Author")
		          .contentType(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("name", is("Dummy Author")));
		
	}
	
	@Test
	@Order(4)
	public void whenAddAuthor_thenCreated() throws Exception
	{	
    	Author author = new Author(); 
    	author.setName("Create Author");
    	author.setEmail("CA@gmail.com");
    	author.setAuthorId((long)3);
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(author);
    	this.mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isCreated())
    			.andExpect(jsonPath("name", is("Create Author")));
	}
	
	@Test
	@Order(5)
	public void whenDeleteAuthor_thenOk() throws Exception
	{
		this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/authors/{name}", "Create Author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
			
	}
	
	@Test
	@Order(6)
	public void whenAuthorAddBook_thenCreated() throws Exception
	{
		Book book = new Book();
		book.setTitle("Create");
		book.setBookId((long)4);
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(book);
    	this.mockMvc.perform(post("/api/authors/createBook/{name}","Dummy Author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isCreated())
    			.andExpect(jsonPath("title", is("Create")));
	}
	
	@Test
	@Order(7)
	public void whenGetBooksByAuthor_thenOk() throws Exception
    {	
    	mockMvc.perform(get("/api/authors/AllBooks/{name}","Dummy Author")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].title", is("Create")));
    }
	
	@Test
	@Order(8)
	public void whenDeleteBookByAuthor_thenOk() throws Exception
	{
		this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/authors/deleteBook/{bookTitle}/{authorName}", "Create","Dummy Author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
			
	}
}
