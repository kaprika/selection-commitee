package by.training.lysiuk.project.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class Enrolee extends AbstractModel {

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String email;

	@Column
	private Date dateOfRegistration;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column
	private String identificationNumber;

	@ManyToOne(targetEntity = PlanSet.class, fetch = FetchType.LAZY)
	private PlanSet planSet;

	@Column
	private Integer certificate;

	@Column
	private String phoneNumber;

	@Transient
	private Integer totalScore;

	@Transient
	public Integer getTotalScore() {
		return totalScore;
	}

	@Transient
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}

	public PlanSet getPlanSet() {
		return planSet;
	}

	public void setPlanSet(PlanSet planSet) {
		this.planSet = planSet;
	}

	public Integer getCertificate() {
		return certificate;
	}

	public void setCertificate(Integer certificate) {
		this.certificate = certificate;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

}
