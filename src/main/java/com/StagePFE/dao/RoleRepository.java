package com.StagePFE.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.StagePFE.entities.Role;

@CrossOrigin("http://localhost:4200")
public interface RoleRepository extends JpaRepository<Role,Long> {


}
