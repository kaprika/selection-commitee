package by.training.lysiuk.project.webapp.component.menu;

import org.apache.wicket.markup.html.link.Link;

import by.training.lysiuk.project.webapp.page.enrollees.EnrolleesPage;
import by.training.lysiuk.project.webapp.page.faculties.FacultiesPage;
import by.training.lysiuk.project.webapp.page.home.HomePage;
import by.training.lysiuk.project.webapp.page.plan.PlanSetPage;
import by.training.lysiuk.project.webapp.page.plan.PlanSetThisYearPage;
import by.training.lysiuk.project.webapp.page.subjects.SubjectsPage;

public class MenuPanelLoggedUser extends MenuPanel {

	public MenuPanelLoggedUser(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("link-logout") {
			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(HomePage.class);
			}
		});
		
		add(new Link("link-enrollees") {
			@Override
			public void onClick() {
				setResponsePage(new EnrolleesPage());
			}
		});
		
		add(new Link("link-subjects") {
			@Override
			public void onClick() {
				setResponsePage(new SubjectsPage());
			}
		});
		
		add(new Link("link-faculties") {
			@Override
			public void onClick() {
				setResponsePage(new FacultiesPage());
			}
		});
	}
}
