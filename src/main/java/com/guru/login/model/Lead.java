package com.guru.login.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "lead")
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lead_id")
	private Integer id;

	@Column(name = "user_name")
	@Length(min = 5, message = "*Your first name must have at least 5 characters")
	@NotEmpty(message = "*Please provide a user first name")
	private String userName;

	@Column(name = "email1", unique = true)
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email1;

	@Column(name = "email2")
	private String email2;

	@Column(name = "email3")
	private String email3;

	@Column(name = "mobile")
	@NotEmpty(message = "*Please provide a mobile number")
	private String mobile;

	@Column(name = "mobile1")
	private String mobile1;

	@Column(name = "mobile2")
	private String mobile2;

	@Column(name = "comments")
	@Length(min = 5, max = 500)
	private String comments;

	@Column(name = "createDate")
	@Temporal(TemporalType.DATE)
	private Date createDate;

	@Column(name = "updateDate")
	@Temporal(TemporalType.DATE)
	private Date updateDate;

	@Column(name = "company_Name")
	@Length(min = 5, message = "*Your company must have at least 5 characters")
	@NotEmpty(message = "*Please provide a company name")
	private String companyName;

	@Column(name = "event_Name")
	@NotEmpty(message = "*Please provide a event name")
	private String eventName;

	@Column(name = "indsutry_type")
	private String indsutryType;

	@Column(name = "status")
	private int status;

	@Column(name = "stage")
	private int stage;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "lead")
	private Address leadAddress;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Address getLeadAddress() {
		return leadAddress;
	}

	public void setLeadAddress(Address leadAddress) {
		this.leadAddress = leadAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getIndsutryType() {
		return indsutryType;
	}

	public void setIndsutryType(String indsutryType) {
		this.indsutryType = indsutryType;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getEmail3() {
		return email3;
	}

	public void setEmail3(String email3) {
		this.email3 = email3;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		if (createDate == null) {
			createDate = new Date();
		}
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		if (updateDate == null) {
			updateDate = new Date();
		}
		this.updateDate = updateDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(int stage) {
		this.stage = stage;
	}

}
