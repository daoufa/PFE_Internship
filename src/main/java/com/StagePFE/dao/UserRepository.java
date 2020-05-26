package com.StagePFE.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.StagePFE.entities.User;

@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<User,Long> {
	User findByUsername(String username);

}
