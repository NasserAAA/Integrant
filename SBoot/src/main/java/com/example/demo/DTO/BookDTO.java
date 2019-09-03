package com.example.demo.DTO;

import java.util.ArrayList;

import lombok.Data;

@Data
public class BookDTO {
		
	
		private String title;
		
		private String author;
		
		private String uniquetitle;
		
		private ArrayList<String> tags;
		
		
}
