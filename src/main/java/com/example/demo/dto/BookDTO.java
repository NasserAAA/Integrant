package com.example.demo.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class BookDTO {
		
	
		private String title;
		
		private String author;
		
		private String uniquetitle;
		
		private ArrayList<String> tags;
		
		
}
