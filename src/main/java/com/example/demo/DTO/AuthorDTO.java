package com.example.demo.DTO;



import java.util.ArrayList;

import lombok.Data;

@Data
public class AuthorDTO {
			
		
		private String name;
		
		private int numBooks;
		
		private ArrayList<String> books;
		
}
