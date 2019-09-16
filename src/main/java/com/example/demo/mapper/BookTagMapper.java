package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.Collection;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.example.demo.dto.BookTagDTO;
import com.example.demo.model.Book;
import com.example.demo.model.BookTag;
import com.example.demo.repository.BookTagRepository;

@Mapper(componentModel="spring")
public interface BookTagMapper {
		
	@Mappings({
        @Mapping(source = "name", target = "name"),
		@Mapping(target="books",ignore= true)
		})
	BookTagDTO TagToDTO(BookTag source,@Context BookTagRepository service);
	
	@AfterMapping
	   default void TagToDTO( @MappingTarget BookTagDTO target, BookTag source,@Context BookTagRepository service) {
			Collection<Book> books = source.getBooks();
			if(!(books==null)) {
			ArrayList<String> strBooks=new ArrayList<>();
			ArrayList<Book> objBooks=new ArrayList<>(books);
			for (int i = 0; i < objBooks.size(); i++) 
				strBooks.add(objBooks.get(i).getTitle());
			target.setBooks(strBooks);
			}
	        target.setName(source.getName());
	   }
	default ArrayList<BookTagDTO> TagsToDTOs(ArrayList<BookTag> tags,@Context BookTagRepository service){
		ArrayList<BookTagDTO> tagsdto=new ArrayList<>();
		ArrayList<BookTag> objTags=new ArrayList<>(tags);
		for(int i=0;i<objTags.size();i++)
			tagsdto.add(TagToDTO(objTags.get(i),service));
		return tagsdto;
	}
	
	@Mappings({
        @Mapping(source = "name", target = "name"),
		@Mapping(target="books",ignore= true)
		})
	BookTag DTOToTag(BookTagDTO source,@Context BookTagRepository service);
	
	default void DTOToTag( @MappingTarget BookTag target, BookTagDTO source,@Context BookTagRepository service ) {
		   BookTag result= service.findByName(source.getName());
	        target.setBooks(result.getBooks());
	}
	
	default Collection<BookTag> DTOsToTags(ArrayList<BookTagDTO> tags,@Context BookTagRepository service){
		ArrayList<BookTag> objTags=new ArrayList<>();
		for(int i=0;i<tags.size();i++)
			objTags.add(DTOToTag(tags.get(i), service));
		return objTags;
	}
	
}
