package by.training.lysiuk.project.webapp.page.plan;

import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.plan.panel.PlanSetThisYearListPanel;

public class PlanSetThisYearPage extends AbstractPage {

	public PlanSetThisYearPage() {
		super();
		add(new PlanSetThisYearListPanel("list-panel"));

	}
}
