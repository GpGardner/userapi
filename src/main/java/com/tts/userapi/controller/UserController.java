package com.tts.userapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.tts.userapi.model.User;
import com.tts.userapi.repository.UserReposity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

	@Autowired
	UserReposity userRepository;

	@GetMapping("/users")
	public List<User> getUsers(@RequestParam(value = "state", required = false) String state) {
		System.out.println(state);

		if(state != null){
			 return userRepository.findByState(state);
		} else {
			return (List<User>) userRepository.findAll();
		}
	}

	// Localhost:8080/users/8
	@GetMapping("/users/{id}")
	public ResponseEntity<Optional<User>> getUser(@PathVariable(value = "id") Long id) {

		Optional<User> user = userRepository.findById(id);

		if(!user.isPresent()){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}

	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
		System.out.println("Binding Result -- : " + bindingResult);
		
		if(bindingResult.hasErrors()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/users/{id}")
	public void editUser(@PathVariable(value="id") Long id, @RequestBody User user){

		Optional<User> requestedUser = userRepository.findById(id);

		//if request user isnot present 
		 //return a notfound httpstatus

		//if there were validation errors
			//return a bad request httpstatus

		//update the user
		userRepository.save(user);
		
		//return a ok httpstatus	

	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id){
		userRepository.deleteById(id);
	}
	

}
