package by.training.lysiuk.project.datamodel;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	@Column
	private String identificationNumber;

	// TODO improve/fix if possible
	@MapsId
	@OneToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(nullable = false, updatable = false, name = "id")
	private Faculty faculty;

	@Column
	private Integer certificate;

	@OneToMany(mappedBy = "enrolee", fetch = FetchType.LAZY)
	private List<ScoresInSubjects> scoresInSubjects;

	public List<ScoresInSubjects> getScoresInSubjects() {
		return scoresInSubjects;
	}

	public void setScoresInSubjects(List<ScoresInSubjects> scoresInSubjects) {
		this.scoresInSubjects = scoresInSubjects;
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

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Integer getCertificate() {
		return certificate;
	}

	public void setCertificate(Integer certificate) {
		this.certificate = certificate;
	}

	protected String getIdentificationNumber() {
		return identificationNumber;
	}

	protected void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

}
