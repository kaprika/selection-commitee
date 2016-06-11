package by.training.lysiuk.project.webapp.component.menu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.webapp.app.AuthorizedSession;
import by.training.lysiuk.project.webapp.page.competition.CompetitionPage;
import by.training.lysiuk.project.webapp.page.login.LoginPage;
import by.training.lysiuk.project.webapp.page.plan.OpenPlanSetPage;
import by.training.lysiuk.project.webapp.page.plan.PlanSetPage;
import by.training.lysiuk.project.webapp.page.plan.PlanSetThisYearPage;
import by.training.lysiuk.project.webapp.page.registration.RegistrationPage;
import by.training.lysiuk.project.webapp.page.students.StudentsPage;

public class MenuPanel extends Panel {

	public MenuPanel(String id) {
		super(id);
		// setRenderBodyOnly(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Link link = new Link("link-plans set this year") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetThisYearPage());
			}
		};
		link.setEnabled(false);
		add(link);

		add(new Link("link-all plans set this year") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetThisYearPage());
			}
		});

		add(new Link("link-open sets") {
			@Override
			public void onClick() {
				setResponsePage(new OpenPlanSetPage());
			}
		});

		add(new Link("link-enrolled students") {
			@Override
			public void onClick() {
				setResponsePage(new StudentsPage());
			}
		});

		add(new Link("link-statistics") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetPage());
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

		Link linkLogin = new Link("link-login") {
			@Override
			public void onClick() {
				setResponsePage(new LoginPage());
			}
		};
		linkLogin.setVisible(!AuthorizedSession.get().isSignedIn());
		add(linkLogin);

	}
}
