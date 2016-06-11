package by.training.lysiuk.project.dataaccess.filters;

public class ScoresInSubjectsFilter extends AbstractFilter {
	
	private boolean isFetchEnrolee;

	public boolean isFetchEnrolee() {
		return isFetchEnrolee;
	}

	public void setFetchEnrolee(boolean isFetchEnrolee) {
		this.isFetchEnrolee = isFetchEnrolee;
	}
	

}
