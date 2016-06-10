package by.training.lysiuk.project.webapp.page.competition;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.competition.panel.CompetitionListPanel;

public class CompetitionPage extends AbstractPage{

	public CompetitionPage() {
		super();
		add(new CompetitionListPanel("list-panel"));
	}

}
