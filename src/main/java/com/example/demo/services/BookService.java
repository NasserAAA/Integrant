package com.example.demo.services;

import java.util.ArrayList;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.BookDTO;
import com.example.demo.DTO.BookTagDTO;
import com.example.demo.exceptionhandler.BookNotFoundException;
import com.example.demo.exceptionhandler.BookTagNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.BookTagMapper;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repo.AuthorRepository;
import com.example.demo.repo.BookRepository;
import com.example.demo.repo.BookTagRepository;

@Service
public class BookService {
	
	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
	private BookTagRepository bookTagRepository;
	
	@Autowired 
	private	AuthorRepository authorRepository;
	
	private BookMapper bookMapper
    = Mappers.getMapper(BookMapper.class);
	
	private BookTagMapper tagMapper
    = Mappers.getMapper(BookTagMapper.class);
	
	 
	 
	 
	 public ArrayList<BookDTO> findAllBooks(){
			ArrayList<BookDTO> list = new ArrayList<>();
			bookRepository.findAll().forEach(e -> list.add( bookMapper.BookToDTO(e,bookRepository)));
			return list;
		}
	 
	 public BookDTO findBookByTitle(String bookTitle , String author){
		 String uniqueTitle = bookTitle+" Book By "+author;
		 Book book = bookRepository.findByUniquetitle(uniqueTitle);
		 BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
		 return bookdto;
		}
	 
	 
    
    public BookDTO findBook(Long id) {
    	Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    	BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
        return bookdto;
          
    }
    
    public BookDTO createBook(Book book) {
    	Author author = book.getAuthor();
    	if(author==null)
    		author= authorRepository.findById((long)1).get();
    	book.setAuthor(author);
    	book.setUniqueTitle();
    	bookRepository.save(book);
    	BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
        return bookdto;
    }
    
    public void deleteBook( Long id) {
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
        
    }
    
    public BookDTO updateBook( Book book,  Long id) {
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        book.setBookId(id);
        bookRepository.save(book);
        BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
        return bookdto;
    }
	
    public BookDTO updateinStock(Long id , int stock) {
        Book book =	 bookRepository.findById(id)
         .orElseThrow(BookNotFoundException::new);
    	 book.setBookId(id);
    	 book.setInstock(true);
    	 book.setStock(stock);
    	 bookRepository.save(book);
         BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
         return bookdto;
    }
    
    public BookDTO updateoutStock(Long id) {
        Book book =	 bookRepository.findById(id)
         .orElseThrow(BookNotFoundException::new);
    	 book.setBookId(id);
    	 book.setInstock(false);
    	 book.setStock(0);
    	 bookRepository.save(book);
         BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
         return bookdto;
    }
    
    public BookDTO updateBookTag( Long bookId,  Long id) {
	        Book book = bookRepository.findById(bookId)
	          .orElseThrow(BookNotFoundException::new);
	        BookTag booktag =  bookTagRepository.findById(id)
	          .orElseThrow(BookTagNotFoundException::new);
	        ArrayList<BookTag> booktag2 =  (ArrayList<BookTag>) book.getTags();
	        ArrayList<BookTag> mergedSet = new ArrayList<BookTag>(); 
	        ArrayList<BookTag> booktag3 = new ArrayList<BookTag>();
	        booktag3.add(booktag);
	        mergedSet.addAll(booktag3); 
	        mergedSet.addAll(booktag2);
	        book.setTags(mergedSet);;
	        book.setBookId(bookId);
	        bookRepository.save(book);
	        BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
	        return bookdto;
	    }
    
    public ArrayList<BookTagDTO> findAllTags(){
		ArrayList<BookTagDTO> list = new ArrayList<>();
		bookTagRepository.findAll().forEach(e -> list.add( tagMapper.TagToDTO(e,bookTagRepository)));
		return list;
		}


    public BookTagDTO createTag(BookTag tag) {
	bookTagRepository.save(tag);
	BookTagDTO bookTagdto =  tagMapper.TagToDTO(tag,bookTagRepository);
    return bookTagdto;
    }

    public void deleteTag( Long id) {
        bookTagRepository.findById(id)
          .orElseThrow(BookTagNotFoundException::new);
        bookTagRepository.deleteById(id);
        
    }
 
    public BookTagDTO updateTagBook( Long tagId,  Long id) {
        BookTag tag = bookTagRepository.findById(tagId)
          .orElseThrow(BookTagNotFoundException::new);
        Book book = bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        ArrayList<Book> book2 = (ArrayList<Book>) tag.getBooks();
        ArrayList<Book> mergedSet = new ArrayList<Book>(); 
        ArrayList<Book> book3 = new ArrayList<Book>();
        book3.add(book);
        mergedSet.addAll(book3); 
        mergedSet.addAll(book2);
        tag.setBooks(mergedSet);
        tag.setTagId(tagId);
        bookTagRepository.save(tag);
        BookTagDTO bookTagdto = tagMapper.TagToDTO(tag,bookTagRepository);
        return bookTagdto;
    }
    
    
    
    
    
    
}
