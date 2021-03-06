package by.training.lysiuk.project.webapp.page;

import java.util.Calendar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.training.lysiuk.project.webapp.app.AuthorizedSession;
import by.training.lysiuk.project.webapp.component.localization.LanguageSelectionComponent;
import by.training.lysiuk.project.webapp.component.menu.MenuPanel;
import by.training.lysiuk.project.webapp.component.menu.MenuPanelLoggedUser;
import by.training.lysiuk.project.webapp.page.home.HomePage;

public abstract class AbstractPage extends WebPage {

	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new LanguageSelectionComponent("language-select"));

		/*
		 * if (getPage().getClass().equals(HomePage.class)) { add(new
		 * MenuPanelHome("menu-panel")); } else { add(new
		 * MenuPanel("menu-panel")); }
		 */
		if (!AuthorizedSession.get().isSignedIn()) {
			add(new MenuPanel("menu-panel"));
		} else {
			add(new MenuPanelLoggedUser("menu-panel"));
		}
		AbstractReadOnlyModel<Integer> yearModel = new AbstractReadOnlyModel<Integer>() {
			@Override
			public Integer getObject() {
				return Calendar.getInstance().get(Calendar.YEAR);
			}
		};

		WebMarkupContainer footer = new WebMarkupContainer("footer");
		add(footer);
		footer.add(new Label("current-year", yearModel));
		footer.add(AttributeModifier.append("onclick", "alert('Im clicked')"));
	}

}
