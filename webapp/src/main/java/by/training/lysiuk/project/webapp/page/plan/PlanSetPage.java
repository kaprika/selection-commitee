package by.training.lysiuk.project.webapp.page.plan;

import org.apache.wicket.markup.html.link.Link;

import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.plan.panel.PlanSetListPanel;

public class PlanSetPage extends AbstractPage {

	public PlanSetPage() {
		super();
		add(new PlanSetListPanel("list-panel"));
		add(new Link("create") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetEditPage(new PlanSet()));
			}
		});
	}
}
