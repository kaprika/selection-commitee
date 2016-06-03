package by.training.lysiuk.project.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class PlanSet extends AbstractModel {

	@Column
	private Date startDateSet;

	@Column
	private Date endDateSet;

	@Column
	private Integer plan;

	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	private Faculty faculty;

	@JoinTable(name = "faculty_subject_on_years", joinColumns = {
			@JoinColumn(name = "plan_set_id") }, inverseJoinColumns = { @JoinColumn(name = "subject_id") })
	@ManyToMany(targetEntity = Subject.class, fetch = FetchType.LAZY)
	private List<Subject> subjects = new ArrayList<Subject>();

	public Date getStartDateSet() {
		return startDateSet;
	}

	public void setStartDateSet(Date startDateSet) {
		this.startDateSet = startDateSet;
	}

	public Date getEndDateSet() {
		return endDateSet;
	}

	public void setEndDateSet(Date endDateSet) {
		this.endDateSet = endDateSet;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
