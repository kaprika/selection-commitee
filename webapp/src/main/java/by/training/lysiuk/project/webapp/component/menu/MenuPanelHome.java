package by.training.lysiuk.project.webapp.component.menu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.training.lysiuk.project.webapp.page.branch.BranchByCertificatePage;
import by.training.lysiuk.project.webapp.page.results.ResultsOfPreviousYearsPage;

public class MenuPanelHome extends MenuPanel{

	public MenuPanelHome(String id) {
		super(id);
	}
	
	 @Override
	    protected void onInitialize() {
	        super.onInitialize();

	        add(new Link("link-branch") {
	            @Override
	            public void onClick() {
	                setResponsePage(new BranchByCertificatePage());
	            }
	        });

	        add(new Link("link-previous results") {
	            @Override
	            public void onClick() {
	                setResponsePage(new ResultsOfPreviousYearsPage());
	            }
	        });
	 }
}
