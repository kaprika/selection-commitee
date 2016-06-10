package by.training.lysiuk.project.webapp.page.plan;

import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.plan.panel.OpenPlanSetListPanel;

public class OpenPlanSetPage extends AbstractPage {

	public OpenPlanSetPage() {
		super();
		add(new OpenPlanSetListPanel("list-panel"));

	}
}
