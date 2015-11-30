package com.techlify.ranchmanager.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author kamal
 *
 */
@Entity
@Table(name = "ANIMAL")
public class Animal {

	@Id
	@Column(name = "ANIMAL_ID")
	@GeneratedValue
	private Long id;

	@Column(name = "NUMBERS")
	private Long numbers;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "TYPE_ID")
	private AnimalType typeId;

	@Column(name = "DATE_OF_BIRTH")
	private Date dateOfBirth;

	@Column(name = "GENDER")
	private String gender;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "FATHER_ID")
	private Animal father;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "MOTHER_ID")
	private Animal mother;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ANIMAL_PHOTO", joinColumns = { @JoinColumn(name = "ANIMAL_ID", referencedColumnName = "ANIMAL_ID") }, inverseJoinColumns = { @JoinColumn(name = "PHOTO_ID", referencedColumnName = "PHOTO_ID") })
	private List<Photo> photos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumbers() {
		return numbers;
	}

	public void setNumbers(Long numbers) {
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

	public Animal getFather() {
		return father;
	}

	public void setFather(Animal father) {
		this.father = father;
	}

	public Animal getMother() {
		return mother;
	}

	public void setMother(Animal mother) {
		this.mother = mother;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.typeId+" "+this.id;
	}
}
