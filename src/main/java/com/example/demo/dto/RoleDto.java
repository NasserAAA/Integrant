package com.example.demo.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class RoleDto {
	private long roleId;
	private String name;
	private ArrayList<String> privileges;
}
