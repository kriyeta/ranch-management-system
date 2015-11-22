package com.techlify.ranchmanager.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kamal
 *
 */
@Entity
@Table(name="ANIMAL_TYPE")
public class AnimalType {
 
    @Id
    @GeneratedValue
    private Long id; 
    private String name;
    private String description;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
     
//    @OneToMany(mappedBy="department",cascade=CascadeType.PERSIST)
//    private  long
}
