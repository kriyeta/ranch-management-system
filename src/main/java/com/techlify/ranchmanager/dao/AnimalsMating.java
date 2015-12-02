package com.techlify.ranchmanager.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author kamal
 *
 */
@Entity
@Table(name = "ANIMAL_MATING")
public class AnimalsMating {
	
	@Id
	@Column(name = "ANIMAL_MATING_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "male parent: "+this.maleParent+" female parent: "+this.femaleParent+" start date: "+startDate+" end date: "+endDate;
	}
	
}
