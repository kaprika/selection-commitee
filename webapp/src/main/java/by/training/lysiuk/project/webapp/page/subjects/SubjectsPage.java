package by.training.lysiuk.project.webapp.page.subjects;

import org.apache.wicket.markup.html.link.Link;

import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.subject.panel.SubjectListPanel;

public class SubjectsPage extends AbstractPage {

	public SubjectsPage() {
		super();
		add(new SubjectListPanel("list-panel"));

		add(new Link("create") {
			@Override
			public void onClick() {
				setResponsePage(new SubjectEditPage(new Subject()));
			}
		});
	}
}