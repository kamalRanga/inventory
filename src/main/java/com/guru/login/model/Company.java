package com.guru.login.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "company_Name")
	@Length(min = 5, message = "*Your company must have at least 5 characters")
	@NotEmpty(message = "*Please provide a company name")
	private String companyName;

	@Column(name = "designation")
	@Length(min = 5, message = "*Your Designation have at least 5 characters")
	private String designation;

	@Column(name = "indsutry_type")
	@Email(message = "*Please provide a valid Indsutry_Type")
	private String indsutryType;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getIndsutryType() {
		return indsutryType;
	}

	public void setIndsutryType(String indsutryType) {
		this.indsutryType = indsutryType;
	}

}
