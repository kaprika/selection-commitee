package by.training.lysiuk.project.webapp.page.students;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.students.panel.StudentListPanel;

public class StudentsPage extends AbstractPage {

	private PlanSetFilter filter;

	public StudentsPage(PageParameters parameters) {
		super(parameters);
	}

	public StudentsPage(PlanSetFilter filter) {
		super();
		this.filter = filter;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<PlanSetFilter>(filter));
		add(form);

		DateTextField startDateField = new DateTextField("startDate");
		startDateField.add(new DatePicker());
		startDateField.setRequired(true);
		startDateField.setLabel(new ResourceModel("start date"));
		form.add(startDateField);

		DateTextField endDateField = new DateTextField("endDate");
		endDateField.add(new DatePicker());
		endDateField.setRequired(true);
		endDateField.setLabel(new ResourceModel("end date"));
		form.add(endDateField);

		form.add(new SubmitLink("show") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				setResponsePage(new StudentsPage(filter));
			}
		});

		add(new StudentListPanel("list-panel", filter));
		add(new FeedbackPanel("feedback"));

	}
}