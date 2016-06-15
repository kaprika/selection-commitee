package by.training.lysiuk.project.webapp.page.home;

import org.apache.wicket.markup.html.panel.FeedbackPanel;

import by.training.lysiuk.project.webapp.page.AbstractPage;

public class HomePage extends AbstractPage {

	public HomePage() {
		super();
		add(new FeedbackPanel("feedback"));
	}

}
