package com.techlify.ranchmanager.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author kamal
 *
 */
@Entity
@Table(name="PHOTO")
public class Photo {
 
    @Id
    @Column(name="PHOTO_ID")
    @GeneratedValue
    private Long id; 
    
    @Column(name="PHOTO")
    private byte[] photo;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="DESCRIPTION")
    private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
}
