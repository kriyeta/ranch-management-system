package com.techlify.ranchmanager.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @author kamal
 *
 */
@Entity
@Table(name="PHOTO")
@DynamicUpdate(value=true)
public class Photo {
 
    @Id
    @Column(name="PHOTO_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    
    @Column(name="PHOTO", columnDefinition = "LONGBLOB")
    private byte[] photo;
    
    @Column(name="NAME")
    private String name;
    
    @Column(name="DESCRIPTION", columnDefinition = "TEXT")
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
