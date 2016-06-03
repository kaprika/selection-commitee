package by.training.lysiuk.project.datamodel.custom;

import java.util.List;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;

public class Competition {

	private Faculty faculty;

	private Integer plan;

	private Long currentAmount;

	private Integer maxPoints;

	private Integer minPoints;
	
	private List<Enrolee> enrolees;

	public List<Enrolee> getEnrolees() {
		return enrolees;
	}

	public void setEnrolees(List<Enrolee> enrolees) {
		this.enrolees = enrolees;
	}

	public Competition() {
		super();
	
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Integer getPlan() {
		return plan;
	}

	public void setPlan(Integer plan) {
		this.plan = plan;
	}

	public Long getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Long currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Integer getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints(Integer maxPoints) {
		this.maxPoints = maxPoints;
	}

	public Integer getMinPoints() {
		return minPoints;
	}

	public void setMinPoints(Integer minPoints) {
		this.minPoints = minPoints;
	}

}
