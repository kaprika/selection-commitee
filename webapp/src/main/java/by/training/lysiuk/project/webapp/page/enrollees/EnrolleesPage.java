package by.training.lysiuk.project.webapp.page.enrollees;

import org.apache.wicket.markup.html.link.Link;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.enrollee.panel.EnrolleeListPanel;
import by.training.lysiuk.project.webapp.page.registration.RegistrationPage;

public class EnrolleesPage  extends AbstractPage {

		public EnrolleesPage() {
			super();
			add(new EnrolleeListPanel("list-panel"));

			add(new Link("add") {
				@Override
				public void onClick() {
					setResponsePage(new RegistrationPage(new Enrolee()));
				}
			});
		}
}
