package com.techlify.ranchmanager.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

/**
 * @author kamal
 *
 */
@Entity
@Table(name = "ANIMAL")
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)
public class Animal {

	@Id
	@Column(name = "ANIMAL_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NUMBERS", unique=true)
	private String numbers;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "TYPE_ID")
	private AnimalType typeId;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "GENDER")
	private String gender;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ANIMAL_PHOTO", joinColumns = { @JoinColumn(name = "ANIMAL_ID", referencedColumnName = "ANIMAL_ID") }, inverseJoinColumns = { @JoinColumn(name = "PHOTO_ID", referencedColumnName = "PHOTO_ID") })
	private List<Photo> photos;

	@ManyToOne
	@JoinColumn(name = "MALE_PARENT_ID")
	private Animal maleParent;

	@ManyToOne
	@JoinColumn(name = "FEMALE_PARENT_ID")
	private Animal femaleParent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public AnimalType getTypeId() {
		return typeId;
	}

	public void setTypeId(AnimalType typeId) {
		this.typeId = typeId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public Animal getMaleParent() {
		return maleParent;
	}

	public void setMaleParent(Animal maleParent) {
		this.maleParent = maleParent;
	}

	public Animal getFemaleParent() {
		return femaleParent;
	}

	public void setFemaleParent(Animal femaleParent) {
		this.femaleParent = femaleParent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "animal #"+this.getNumbers() + " ( id: " + this.getId() + ")";
	}
}
