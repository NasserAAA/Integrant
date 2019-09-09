package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
public class BookControllerTest {
	
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
    public void whenGetBooks_thenOk() throws Exception
    {	Book book = new Book();
		book.setTitle("Dummy Title");
    	book.setUniquetitle("Dummy Title Book By Dum Author");
    	Author author = new Author(); 
    	author.setName("Dum Author");
    	author.setEmail("DDA@gmail.com");
    	authorRepository.save(author);
    	book.setAuthor(author);
    	BookTag bookTag = new BookTag("Test");
    	bookTagRepository.save(bookTag);
    	book.setTags(new ArrayList<>(Arrays.asList(bookTag)));
    	bookRepository.save(book);
    	mockMvc.perform(get("/api/books")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].title", is("Dummy Title")));
    }
	
	@Test
	@Order(2)
	public void whenUpdateInStockBook_thenOk() throws Exception
	{
		mockMvc.perform(put("/api/books/inStock/{uniqueTitle}/{stock}", "Dummy Title Book By Dum Author","200")
		          .contentType(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("title", is("Dummy Title")));
		
	}
	
	@Test
	@Order(3)
	public void whenFindBookByTitle_thenOk() throws Exception
	{
		mockMvc.perform(get("/api/books/title/{bookTitle}/{author}","Dummy Title","Dum Author")
		          .contentType(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("title", is("Dummy Title")));
	}
	
	@Test
	@Order(4)
	public void whenAddTagToBook_thenOk() throws Exception
	{
		mockMvc.perform(get("/api/books/findAllTagsinBook/{uniqueTitle}","Dummy Title Book By Dum Author")
		          .contentType(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("$[0].name", is("Test")));
		
	}
	
	@Test
	@Order(5)
	public void whenAddBook_thenCreated() throws Exception
	{	
		Book book = new Book();
		book.setTitle("Test Create");
    	book.setUniquetitle("Test Create Book By Create Author");
    	Author author = new Author(); 
    	author.setName("Create Author");
    	author.setEmail("CA@gmail.com");
    	authorRepository.save(author);
    	book.setAuthor(author);
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(book);
        System.out.println(book);
    	this.mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isCreated())
    			.andExpect(jsonPath("title", is("Test Create")));
	}
	
	@Test
	@Order(6)
	public void whenDeleteBook_thenOk() throws Exception
	{
		this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/books/{booktitle}/{author}", "Test Create","Create Author")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
			
	}
	
	@Test
	@Order(7)
    public void whenGetTags_thenOk() throws Exception
    {	
    	mockMvc.perform(get("/api/books/tags")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].name", is("Test")));
    }
	
	@Test
	@Order(8)
	public void whenAddTag_thenCreated() throws Exception
	{	
		BookTag bookTag = new BookTag("Create");
    	ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bookTag);
    	this.mockMvc.perform(post("/api/books/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isCreated())
    			.andExpect(jsonPath("name", is("Create")));
	}
	
	@Test
	@Order(9)
	public void whenDeleteTag_thenOk() throws Exception
	{
		this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/books/tags/{name}", "Create")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
			
	}
	
	@Test
	@Order(10)
	public void whenGetBooksinTags_thenOk() throws Exception
	{
		mockMvc.perform(get("/api/books/findAllBooksinTag/{name}","Test")
		          .contentType(MediaType.APPLICATION_JSON))
		          .andExpect(status().isOk())
		          .andExpect(jsonPath("$[0].title", is("Dummy Title")));
	}
}
