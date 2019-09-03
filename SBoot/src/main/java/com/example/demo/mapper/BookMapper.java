package com.example.demo.mapper;


import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import com.example.demo.DTO.BookDTO;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repo.BookRepository;



@Mapper(componentModel="spring")
public interface BookMapper {
	
	@Mappings({
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "uniquetitle", target = "uniquetitle"),
		@Mapping(target="tags",ignore= true),
        @Mapping(target="author",ignore=true)
		})
	BookDTO BookToDTO(Book source,@Context BookRepository service);
	
	@AfterMapping
	   default void BookToDTO( @MappingTarget BookDTO target, Book source,@Context BookRepository service) {
			Collection<BookTag> tags = source.getTags();
			if(!(tags==null)) {
			ArrayList<String> strTags=new ArrayList<>();
			ArrayList<BookTag> objTags=new ArrayList<>(tags);
			for (int i = 0; i < objTags.size(); i++) 
			strTags.add(objTags.get(i).getName());
			target.setTags(strTags);
			}
	        target.setAuthor(source.getAuthor().getName());
	   }
	
		default ArrayList<BookDTO> BooksToDTOs(ArrayList<Book> books,@Context BookRepository service){
		ArrayList<BookDTO> booksdto=new ArrayList<>();
		ArrayList<Book> objBooks=new ArrayList<>(books);
		for(int i=0;i<objBooks.size();i++)
			booksdto.add(BookToDTO(objBooks.get(i),service));
		return booksdto;
	}
	
	@Mappings({
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "uniquetitle", target = "uniquetitle"),
		@Mapping(target="tags",ignore= true),
        @Mapping(target="author",ignore=true)
		})
	Book DTOToBook(BookDTO source, @Context BookRepository service );
	@AfterMapping
	   default void DTOToBook( @MappingTarget Book target, BookDTO source,@Context BookRepository service ) {
		   Book result= service.findByUniquetitle(source.getUniquetitle());
	        target.setAuthor(result.getAuthor());
	        target.setTitle(result.getTitle());
	        target.setTags(result.getTags());
	        target.setUniquetitle(result.getUniquetitle());
	}
	default Collection<Book> DTOsToBooks(ArrayList<BookDTO> books,@Context BookRepository service){
		ArrayList<Book> objBooks=new ArrayList<>();
		for(int i=0;i<books.size();i++)
			objBooks.add(DTOToBook(books.get(i), service));
		return objBooks;
	}



}
