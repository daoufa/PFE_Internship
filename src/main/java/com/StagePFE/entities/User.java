package com.StagePFE.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @NotEmpty(message = "*Please provide a username")
    @Column(length = 50)
    private String username;
    
  
    @NotEmpty(message = "*Please provide your password")
    @Column(length = 100)
    private String password;
    
    
    private Boolean active;
    
    
    @ManyToMany
    @JoinTable(
    		joinColumns = @JoinColumn(name = "username"), 
    		inverseJoinColumns = @JoinColumn(name = "role"))
    private List<Role> roles;
    
    public void addRole(Role role1) {
		if (roles == null) {
			roles = new ArrayList<>();
		}
		roles.add(role1);

	}
}
