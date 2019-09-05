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
import com.example.demo.model.BookTag;
import com.example.demo.repository.BookTagRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@ActiveProfiles("test")

public class BookTagRepositoryTest {
	
	@Autowired
	private TestEntityManager em;
	
	@Autowired
	private BookTagRepository bookTagRepository;
	
	@Test
	public void whenFindByName_thenReturnBookTag() {
		BookTag tag = new BookTag();
		tag.setName("Drama");
		em.persist(tag);
		em.flush();
		BookTag found = bookTagRepository.findByName(tag.getName());
		assertThat(found.getName()).isEqualTo(tag.getName());
	}
	
	@Test
	public void whenFindById_thenReturnBookTag() {
		BookTag tag = new BookTag();
		tag.setName("History");
		em.persist(tag);
		em.flush();
		Optional<BookTag> found = bookTagRepository.findById(tag.getTagId());
		if(found.isPresent())
	    	assertThat(found.get().getName()).isEqualTo(tag.getName());
	    else
	    	assertThat(true).isEqualTo(false);
	}
	

}
