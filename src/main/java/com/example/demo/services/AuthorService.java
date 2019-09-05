package com.example.demo.services;

import java.util.ArrayList;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.AuthorDTO;
import com.example.demo.DTO.BookDTO;
import com.example.demo.exceptionhandler.AuthorNotFoundException;
import com.example.demo.exceptionhandler.BookIdMismatchException;
import com.example.demo.exceptionhandler.BookNotFoundException;
import com.example.demo.mapper.AuthorMapper;
import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	private AuthorMapper authorMapper
    = Mappers.getMapper(AuthorMapper.class);
	
	private BookMapper bookMapper
    = Mappers.getMapper(BookMapper.class);
	
	
	
	public ArrayList<AuthorDTO> findAllAuthors(){
		ArrayList<AuthorDTO> list = new ArrayList<>();
		authorRepository.findAll().forEach(e -> list.add( authorMapper.AuthorToDTO(e,authorRepository)));
		return list;
	}
	
	public AuthorDTO findAuthorByEmail(String email){
		 Author author = authorRepository.findByEmail(email);
		 AuthorDTO authordto = authorMapper.AuthorToDTO(author,authorRepository);
		 return authordto;
		}
	
	public AuthorDTO findAuthor(String name) {
    	Author author = authorRepository.findByName(name);
    	if(author==null)
    		return null;
    	AuthorDTO authordto = authorMapper.AuthorToDTO(author,authorRepository);
        return authordto;
          
    }
	
	public AuthorDTO createAuthor(Author author) {
    	authorRepository.save(author);
    	AuthorDTO authordto = authorMapper.AuthorToDTO(author,authorRepository);
        return authordto;
    }
	
	public void deleteAuthor( String name) {
         Author author = authorRepository.findByName(name);
         if(!(author==null)) {
         Long id = author.getAuthorId();	 
         authorRepository.deleteById(id);
         }
        
    }
	
	 public AuthorDTO updateAuthor( Author author, String name) {
	        Author newAuthor = authorRepository.findByName(name);
	        author.setAuthorId(newAuthor.getAuthorId());
	        authorRepository.save(author);
	        AuthorDTO authordto = authorMapper.AuthorToDTO(author,authorRepository);
	        return authordto;
	    }
		
	
	public BookDTO authorAddBook(String name , BookDTO book ) {
		Author author = authorRepository.findByName(name);
		if(author==null)
			return null;
		Book book2 = new Book();
		book2.setTitle(book.getTitle());
		book2.setAuthor(author);
		book2.setUniqueTitle();
     	bookRepository.save(book2);
		BookDTO bookdto = bookMapper.BookToDTO(book2,bookRepository);
		return bookdto;
	}
	
	public void authorDeleteBook(String bookTitle, String authorName) {
		String uniqueTitle = bookTitle+" Book By "+authorName;
		Book book = bookRepository.findByUniquetitle(uniqueTitle);
		if(book.getAuthor().getName().equals(authorName)) {
			Long book_id = book.getBookId();
			bookRepository.deleteById(book_id);
		}
		else
			throw new BookIdMismatchException();
	}
	
	 public ArrayList<BookDTO> findAllBooksByAuthor(String name){
			ArrayList<BookDTO> list = new ArrayList<>();
			Author author = authorRepository.findByName(name);
			if(author==null)
				return null;
			bookRepository.findAllBooksByAuthor(author).forEach(e -> list.add(bookMapper.BookToDTO(e,bookRepository)));
			return list;
		}
	 
	
}
