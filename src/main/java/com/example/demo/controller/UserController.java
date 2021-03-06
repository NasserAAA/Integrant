package com.example.demo.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//import com.javatpoint.dto.MyMapper;
import com.example.demo.dto.UserFront;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.UserRecord;
import com.example.demo.services.RoleService;
import com.example.demo.services.UserService;
import com.example.demo.validations.ValidationSequence;


@SuppressWarnings("unused")
@RestController
public class UserController {  
	@Autowired  
    private UserService userService; 
	@Autowired  
    private RoleService roleService;
	private UserMapper mapper
    = Mappers.getMapper(UserMapper.class);
	UserController(UserService userService) {

   this.userService = userService;
 }

	@GetMapping("/users")
	  ArrayList<UserFront> getAllUsers() {
		return mapper.usersToFronts(userService.getAllUsers(),roleService);
	  }

    @PostMapping("/admin/users/adduser")
    UserFront newUser(@Validated(ValidationSequence.class) @RequestBody UserRecord newUser) {
	    return mapper.userToFront(userService.addUser(newUser), roleService);
	  }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @GetMapping("/users/{id}")
	  UserFront getUser(@PathVariable Long id) throws UserNotFoundException {
	    return mapper.userToFront(userService.getUser(id)
	      .orElseThrow(() -> new UserNotFoundException(id)), roleService);
	  }
	  @DeleteMapping("/users/{id}")
	  void deleteUser(@PathVariable Long id) {
	    userService.deleteUser(id);
	  }
	  
		//all users with resource hateaos

	//  @GetMapping("/users")
//		  Resources<Resource<UserRecord>> getAllUsers() {
	//
//		    List<Resource<UserRecord>> users = userService.getAllUsers().stream()
//		      .map(assembler::toResource)
//		      .collect(Collectors.toList());
	//
//		    return new Resources<>(users,
//		      linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
//		  }
//	    @GetMapping("/users/{id}")
//		  Resource<UserRecord> getUser(@PathVariable Long id) throws UserNotFoundException {
	//
//	    	UserRecord user = userService.getUser(id)
//		      .orElseThrow(() -> new UserNotFoundException(id));
	//
//		    return assembler.toResource(user);
//		  }
//	    @PutMapping("/users/{id}")
//		  ResponseEntity<?> replaceUser(@RequestBody UserRecord newUser, @PathVariable Long id) throws URISyntaxException {
	//
//	    	UserRecord updatedUser = userService.getUser(id)
//		      .map(user -> {
//		        user.setName(newUser.getName());
//		        user.setEmail(newUser.getEmail());
//		        return userService.addUser(user);
//		      })
//		      .orElseGet(() -> {
//		    	  newUser.setUser_id(id);
//		        return userService.addUser(newUser);
//		      });
	//
//		    Resource<UserRecord> resource = assembler.toResource(updatedUser);
	//
//		    return ResponseEntity
//		      .created(new URI(resource.getId().expand().getHref()))
//		      .body(resource);
//		  }

}  