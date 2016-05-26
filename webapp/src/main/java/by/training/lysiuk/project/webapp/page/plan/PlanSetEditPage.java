package by.training.lysiuk.project.webapp.page.plan;

import javax.inject.Inject;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;

import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.service.FacultyService;
import by.training.lysiuk.project.service.PlanSetService;
import by.training.lysiuk.project.webapp.common.FacultyChoiceRenderer;
import by.training.lysiuk.project.webapp.page.AbstractPage;

public class PlanSetEditPage extends AbstractPage {

	@Inject
	private PlanSetService planSetService;
	
	@Inject
	private FacultyService facultyService;

	private PlanSet planSet;

	public PlanSetEditPage(PageParameters parameters) {
		super(parameters);
	}

	public PlanSetEditPage(PlanSet planSet) {
		super();
		this.planSet = planSet;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<PlanSet>(planSet));
		add(form);

		DateTextField yearField = new DateTextField("year");
		yearField.add(new DatePicker());
		yearField.setRequired(true);
		form.add(yearField);
		
       /* Faculty faculty = planSet.getFaculty();
		TextField<String> facultyField = new TextField<>("faculty.getName()");
		facultyField.setRequired(true);
		form.add(facultyField);*/
		
		Faculty faculty = planSet.getFaculty();
		DropDownChoice<Faculty> facultyField = new DropDownChoice<Faculty>("name", new PropertyModel<Faculty>(faculty, "name"), facultyService.getAll(), FacultyChoiceRenderer.INSTANCE);
        facultyField.setRequired(true);
        form.add(facultyField);

		TextField<Integer> planField = new TextField<>("plan");
		planField.add(RangeValidator.<Integer> range(0, 1_000));
		planField.setRequired(true);
		form.add(planField);

//		DateTextField endDateSetField = new DateTextField("endDateSet");
//		endDateSetField.add(new DatePicker());
//		endDateSetField.setRequired(true);
//		form.add(endDateSetField);
//	
//		Subject subject = planSet.getSubjects().get(0);
//		TextField<String> firstSubjectField = new TextField<>("first subject", new PropertyModel<>(subject, "name"));
//		firstSubjectField.setRequired(true);
//		form.add(firstSubjectField);
//		
//        subject = planSet.getSubjects().get(1);
//		TextField<String> secondSubjectField = new TextField<>("second subject",new PropertyModel<>(subject, "name"));
//		secondSubjectField.setRequired(true);
//		form.add(secondSubjectField);
//		
//		subject = planSet.getSubjects().get(2);
//		TextField<String> thirdSubjectField = new TextField<>("third subject", new PropertyModel<>(subject, "name"));
//		thirdSubjectField.setRequired(true);
//		form.add(thirdSubjectField);
		
		/*
		 * TextField<Double> basePriceField = new TextField<>("basePrice");
		 * basePriceField.add(RangeValidator.<Double> range(0d, 1_000_000d));
		 * basePriceField.setRequired(true); form.add(basePriceField);
		 */
//
//		CheckBox activeField = new CheckBox("active");
//		form.add(activeField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				planSetService.saveOrUpdate(planSet);
				setResponsePage(new PlanSetPage());
			}
		});

		add(new FeedbackPanel("feedback"));

		add(new Link("cancel") {

			@Override
			public void onClick() {
				setResponsePage(new PlanSetPage());
			}
		});

	}
	/*
	 * private class UniqueNameValidator implements IValidator<String> {
	 * 
	 * @Override public void validate(IValidatable<String> validatable) {
	 * 
	 * Faculty faculty = facultyService.getByName(validatable.getValue()); if
	 * (faculty != null) { ValidationError error = new ValidationError();
	 * error.setMessage(getClass().getSimpleName() + " already exists");
	 * validatable.error(error); } }
	 * 
	 * }
	 */

}