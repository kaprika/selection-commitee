package by.training.lysiuk.project.webapp.page.product;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.webapp.page.AbstractPage;

public class FacultyDetailsPage extends AbstractPage {

    public FacultyDetailsPage(PageParameters parameters) {
        super(parameters);
    }

    public FacultyDetailsPage(Faculty faculty) {
        super();

    }

}
