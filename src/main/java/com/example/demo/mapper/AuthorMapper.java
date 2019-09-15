package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.Collection;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import com.example.demo.DTO.AuthorDTO;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;


@Mapper(componentModel="spring")
public interface AuthorMapper {
	
	@Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source="numBooks",target = "numBooks" ),
		@Mapping(target="books",ignore= true)
		})
	AuthorDTO AuthorToDTO(Author source,@Context AuthorRepository service);
	
	@AfterMapping
	   default void AuthorToDTO( @MappingTarget AuthorDTO target, Author source,@Context AuthorRepository service) {
			Collection<Book> books = source.getBooks();
			if(!(books==null)) {
			ArrayList<String> strBooks=new ArrayList<>();
			ArrayList<Book> objBooks=new ArrayList<>(books);
			for (int i = 0; i < objBooks.size(); i++) 
				strBooks.add(objBooks.get(i).getTitle());
			target.setBooks(strBooks);
			}
			target.setNumBooks(source.getNumBooks());
	        target.setName(source.getName());
	   }
	
	default ArrayList<AuthorDTO> AuthorsToDTOs(ArrayList<Author> authors,@Context AuthorRepository service){
		ArrayList<AuthorDTO> authorsdto=new ArrayList<>();
		ArrayList<Author> objAuthors=new ArrayList<>(authors);
		for(int i=0;i<objAuthors.size();i++)
			authorsdto.add(AuthorToDTO(objAuthors.get(i),service));
		return authorsdto;
	}
	
	@Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source="numBooks",target = "numBooks" ),
		@Mapping(target="books",ignore= true)
		})
	Author DTOToAuthor(AuthorDTO source,@Context AuthorRepository service);
	
	default void DTOToAuthor( @MappingTarget Author target, AuthorDTO source,@Context AuthorRepository service ) {
		   Author result= service.findByName(source.getName());
	        target.setBooks(result.getBooks());
	        target.setNumBooks(result.getNumBooks());
	}
	
	default Collection<Author> DTOsToAuthors(ArrayList<AuthorDTO> authors,@Context AuthorRepository service){
		ArrayList<Author> objAuthors=new ArrayList<>();
		for(int i=0;i<authors.size();i++)
			objAuthors.add(DTOToAuthor(authors.get(i), service));
		return objAuthors;
	}
	
}
