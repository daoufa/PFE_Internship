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
}