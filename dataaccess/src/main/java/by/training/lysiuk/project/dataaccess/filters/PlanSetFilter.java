package by.training.lysiuk.project.dataaccess.filters;

import java.util.Date;

import by.training.lysiuk.project.datamodel.Faculty;

public class PlanSetFilter extends AbstractFilter {

	private Date year;

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

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public boolean isFetchFaculty() {
		return isFetchFaculty;
	}

	public void setFetchFaculty(boolean isFetchFaculty) {
		this.isFetchFaculty = isFetchFaculty;
	}

}
