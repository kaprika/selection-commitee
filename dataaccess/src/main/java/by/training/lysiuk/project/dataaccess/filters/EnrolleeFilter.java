package by.training.lysiuk.project.dataaccess.filters;

public class EnrolleeFilter extends AbstractFilter {
	
	private boolean isFetchPlanSet;

	private boolean isFetchFaculty;
	
	private boolean isFetchSubjects;

	public boolean isFetchPlanSet() {
		return isFetchPlanSet;
	}

	public void setFetchPlanSet(boolean isFetchPlanSet) {
		this.isFetchPlanSet = isFetchPlanSet;
	}

	public boolean isFetchFaculty() {
		return isFetchFaculty;
	}

	public void setFetchFaculty(boolean isFetchFaculty) {
		this.isFetchFaculty = isFetchFaculty;
	}

	public boolean isFetchSubjects() {
		return isFetchSubjects;
	}

	public void setFetchSubjects(boolean isFetchSubjects) {
		this.isFetchSubjects = isFetchSubjects;
	}
}
