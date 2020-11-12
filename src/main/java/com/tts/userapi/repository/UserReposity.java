package com.tts.userapi.repository;

import java.util.List;

import com.tts.userapi.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposity extends CrudRepository<User, Long> {
	
	List<User> findByState(String state);

}
