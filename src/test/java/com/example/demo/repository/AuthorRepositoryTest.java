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
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ActiveProfiles("test")
public class AuthorRepositoryTest {
	
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Test
	public void whenFindByName_thenReturnAuthor() {
		Author author = new Author();
		author.setName("Yuval Noah");
		author.setEmail("YN@gmail.com");
		em.persist(author);
		em.flush();
		Author found = authorRepository.findByName(author.getName());
		assertThat(found.getName()).isEqualTo(author.getName());
	}
	
	@Test
	public void whenFindByEmail_thenReturnAuthor() {
		Author author = new Author();
		author.setName("Yuval Noah");
		author.setEmail("YN@gmail.com");
		em.persist(author);
		em.flush();
		Author found = authorRepository.findByEmail(author.getEmail());
		assertThat(found.getEmail()).isEqualTo(author.getEmail());
	}
	
	@Test
	public void whenFindById_thenReturnAuthor() {
		Author author = new Author();
		author.setName("Yuval Noah");
		author.setEmail("YN@gmail.com");
		em.persist(author);
		em.flush();
		Optional<Author> found = authorRepository.findById(author.getUserId());
		if(found.isPresent())
	    	assertThat(found.get().getName()).isEqualTo(author.getName());
	    else
	    	assertThat(true).isEqualTo(false);
	}

}
