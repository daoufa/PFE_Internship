package com.StagePFE.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
	
    @Id
    @Column(length = 50)
    private String role;
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    
    @Override
    public boolean equals(Object o) {
    	if(o==this) return true;
    	if(!(o instanceof Role))return false;
    	Role r = (Role)o;
    	if(o==null) return false;
    	return ( this.getRole().equals(r.getRole()));
   }
}