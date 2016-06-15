package by.training.lysiuk.project.webapp.page.plan;

import java.io.Serializable;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.plan.panel.PlanSetListPanel;

public class PlanSetPage extends AbstractPage {

	public PlanSetPage() {
		super();
		add(new PlanSetListPanel("list-panel"));
		add(new InvisibleLink("create") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetEditPage(new PlanSet(), true));
			}
		});
		add(new FeedbackPanel("feedback"));
	}

	@AuthorizeAction(roles = { "admin" }, action = Action.RENDER)
	private class InvisibleLink extends Link {

		public InvisibleLink(String id) {
			super(id);
		}

		@Override
		public void onClick() {
		}

	}

}
//package by.training.lysiuk.project.webapp.page.plan;
//
//import org.apache.wicket.markup.html.link.Link;
//import org.apache.wicket.markup.html.panel.FeedbackPanel;
//
//import by.training.lysiuk.project.datamodel.PlanSet;
//import by.training.lysiuk.project.webapp.page.AbstractPage;
//import by.training.lysiuk.project.webapp.page.plan.panel.PlanSetListPanel;
//
//public class PlanSetPage extends AbstractPage {
//
//	public PlanSetPage() {
//		super();
//		add(new PlanSetListPanel("list-panel"));
//		add(new Link("create") {
//			@Override
//			public void onClick() {
//				setResponsePage(new PlanSetEditPage(new PlanSet(), true));
//			}
//		});
//		add(new FeedbackPanel("feedback"));
//	}
//}
