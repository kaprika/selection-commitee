package by.training.lysiuk.project.webapp.page.faculties;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.faculty.panel.FacultyListPanel;

public class FacultiesPage extends AbstractPage {

	public FacultiesPage() {
		super();
		add(new FacultyListPanel("list-panel"));

		add(new Link("create") {
			@Override
			public void onClick() {
				setResponsePage(new FacultyEditPage(new Faculty()));
			}
		});
		add(new FeedbackPanel("feedback"));
	}
}
