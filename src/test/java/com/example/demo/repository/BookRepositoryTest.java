package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.demo.TestConfiguration;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ActiveProfiles("test")
public class BookRepositoryTest {
	
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void whenFindByUniquetitle_thenReturnBook() {
		Book book = new Book();
		book.setTitle("Book1");
		book.setUniquetitle("Book1 Book By Author");
		em.persist(book);
		em.flush();
		Book found = bookRepository.findByUniquetitle(book.getUniquetitle());
		assertThat(found.getUniquetitle()).isEqualTo(book.getUniquetitle());
	}
	
	@Test
	public void whenFindById_thenReturnBook() {
		Book book = new Book();
		book.setTitle("Book1");
		book.setUniquetitle("Book1 Book By Author");
		em.persist(book);
		em.flush();
		Optional<Book> found = bookRepository.findById(book.getBookId());
		if(found.isPresent())
	    	assertThat(found.get().getUniquetitle()).isEqualTo(book.getUniquetitle());
	    else
	    	assertThat(true).isEqualTo(false);
		
	}
	
}
