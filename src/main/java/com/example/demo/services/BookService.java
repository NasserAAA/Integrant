package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.DTO.BookDTO;
import com.example.demo.DTO.BookTagDTO;
import com.example.demo.exceptionhandler.BookNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.BookTagMapper;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookTagRepository;

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
    
    public void deleteBook(String booktitle,String author) {
    	String uniqueTitle = booktitle+" Book By "+author;
        Book book = bookRepository.findByUniquetitle(uniqueTitle);
    	Long id = book.getBookId();
        bookRepository.deleteById(id);
        
    }
    
    public void deleteBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        bookRepository.deleteById(id);
        
    }
	
    public BookDTO updateinStock(String uniqueTitle, int stock) {
    	 Book book = bookRepository.findByUniquetitle(uniqueTitle);
    	 Long id = book.getBookId();
    	 book.setBookId(id);
    	 book.setInstock(true);
    	 book.setStock(stock);
    	 bookRepository.save(book);
         BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
         return bookdto;
    }
    
    public BookDTO updateoutStock(String uniqueTitle) {
         Book book = bookRepository.findByUniquetitle(uniqueTitle);
   	     Long id = book.getBookId();
    	 book.setBookId(id);
    	 book.setInstock(false);
    	 book.setStock(0);
    	 bookRepository.save(book);
         BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
         return bookdto;
    }
    
    public BookDTO updateBookTags(String uniqueTitle,ArrayList<String> tags) {
    	Book book = bookRepository.findByUniquetitle(uniqueTitle);
    	ArrayList<BookTag> booktag = new ArrayList<BookTag>();
    	if(book.getTags()!=null)
    	 booktag =  new ArrayList<BookTag>(book.getTags());
    	for(int i = 0;i<tags.size();i++) {
    		BookTag strTag = bookTagRepository.findByName(tags.get(i));
    		if(strTag==null) {
    			strTag = new BookTag(tags.get(i));
    			bookTagRepository.save(strTag);
    		}
    		if(!(booktag.contains(strTag)))
    			booktag.add(strTag);
    			}
    	book.setTags(booktag);
    	bookRepository.save(book);
    	BookDTO bookdto = bookMapper.BookToDTO(book,bookRepository);
        return bookdto;
    }
    
    public ArrayList<BookTagDTO> findAllTagsinBook(String uniqueTitle){
    	Book book = bookRepository.findByUniquetitle(uniqueTitle);
    	if(book==null)
    		return null;
    	ArrayList<BookTag> booktag = new ArrayList<BookTag>(book.getTags());
    	ArrayList<BookTagDTO> booktagdto = tagMapper.TagsToDTOs(booktag, bookTagRepository);
    	return booktagdto;
    }
    
    /*
     * 
     * 
     * BookTag Service
     * 
     * 
     */
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

    public void deleteTag( String name) {
        BookTag booktag = bookTagRepository.findByName(name);
        Long id = booktag.getTagId();
        bookTagRepository.deleteById(id);
        
    }
    
    public void deleteTagById(Long id) {
        Optional<BookTag> booktag = bookTagRepository.findById(id);
        bookTagRepository.deleteById(id);
        
    }
 
    public ArrayList<BookDTO> findAllBooksinTag(String name){
    	BookTag booktag = bookTagRepository.findByName(name);
    	if(booktag==null)
    		return null;
    	ArrayList<Book> book = new ArrayList<Book>(booktag.getBooks());
    	ArrayList<BookDTO> bookdto = bookMapper.BooksToDTOs(book, bookRepository);
    	return bookdto;
    }
    
    
    
    
    
    
    
}
