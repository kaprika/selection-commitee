package by.training.lysiuk.project.webapp.component.menu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.webapp.page.competition.CompetitionPage;
import by.training.lysiuk.project.webapp.page.faculties.FacultiesPage;
import by.training.lysiuk.project.webapp.page.plan.PlanSetPage;
import by.training.lysiuk.project.webapp.page.signup.RegistrationPage;
import by.training.lysiuk.project.webapp.page.subjects.SubjectsPage;

public class MenuPanel extends Panel {

	public MenuPanel(String id) {
		super(id);
		// setRenderBodyOnly(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Link("link-plan set") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetPage());
			}
		});

		add(new Link("link-faculties") {
			@Override
			public void onClick() {
				setResponsePage(new FacultiesPage());
			}
		});
		
		add(new Link("link-subjects") {
			@Override
			public void onClick() {
				setResponsePage(new SubjectsPage());
			}
		});

		add(new Link("link-competition") {
			@Override
			public void onClick() {
				setResponsePage(new CompetitionPage());
			}
		});

		add(new Link("link-registration") {
			@Override
			public void onClick() {
				setResponsePage(new RegistrationPage(new Enrolee()));
			}
		});

	}
}
