package com.example.demo.dto;

import java.util.ArrayList;


import lombok.Data;

@Data
public class UserFront {
	private long userId;
	private String email;
	private String name;
	private ArrayList<String> roles;
}
