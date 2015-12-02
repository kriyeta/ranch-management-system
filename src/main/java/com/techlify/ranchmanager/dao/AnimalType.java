package com.techlify.ranchmanager.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
	@Column(name = "NAME")
    private String name;
	
	@Column(name = "DESCRIPTION", columnDefinition = "TEXT")
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
     
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName() + " (" + this.getId() + ")";
	}
}
