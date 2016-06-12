package by.training.lysiuk.project.dataaccess.filters;

import java.util.Date;

import by.training.lysiuk.project.datamodel.Faculty;

public class PlanSetFilter extends AbstractFilter {

	private Date startDate;
	
	private Date endDate;

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

	private String facultyName;

	public String getFacultyName() {
		return facultyName;
	}

	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}

	private boolean isFetchFaculty;

	private boolean isFetchSubjects;

	public boolean isFetchSubjects() {
		return isFetchSubjects;
	}

	public void setFetchSubjects(boolean isFetchSubjects) {
		this.isFetchSubjects = isFetchSubjects;
	}

	public boolean isFetchFaculty() {
		return isFetchFaculty;
	}

	public void setFetchFaculty(boolean isFetchFaculty) {
		this.isFetchFaculty = isFetchFaculty;
	}

}
