package by.training.lysiuk.project.webapp.page.faculty;

import javax.inject.Inject;

import by.training.lysiuk.project.service.FacultyService;
import by.training.lysiuk.project.webapp.page.AbstractPage;

public class FacultyPage extends AbstractPage {

    @Inject
    private FacultyService facultyService;

    public FacultyPage() {
        super();
    }
    
    @Override
	protected void onInitialize() {
		super.onInitialize();
	}


}
