package by.training.lysiuk.project.webapp.page.signup;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.service.EnroleeService;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.home.HomePage;

public class RegistrationPage extends AbstractPage {

	@Inject
	private EnroleeService enroleeService;

	private Enrolee enrolee;

	public RegistrationPage(PageParameters parameters) {
		super(parameters);
	}

	public RegistrationPage(Enrolee enrolee) {
		super();
		this.enrolee = enrolee;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<Enrolee>(enrolee));
		add(form);

//		DateTextField yearField = new DateTextField("year");
//		yearField.add(new DatePicker());
//		yearField.setRequired(true);
//		form.add(yearField);

		TextField<String> firstNameField = new TextField<>("firstName");
		firstNameField.setRequired(true);
		form.add(firstNameField);

		TextField<String> lastNameField = new TextField<>("lastName");
		lastNameField.setRequired(true);
		form.add(lastNameField);
		

		TextField<String> emailField = new TextField<>("email");
		emailField.setRequired(true);
		form.add(emailField);
		

		TextField<String> facultyField = new TextField<>("faculty.getName()");
		facultyField.setRequired(true);
		form.add(facultyField);
		
		TextField<Integer> certificateField = new TextField<>("certificate");
		certificateField.add(RangeValidator.<Integer>range(0, 100));
		certificateField.setRequired(true);
		form.add(certificateField);
		
		//scores in subjects
		

		//
		// TextField<String> firstSubjectField = new
		// TextField<>("subject.get(0).getName()");
		// System.out.println(planSet.getSubject().get(0).getName());
		// firstSubjectField.setRequired(true);
		// form.add(firstSubjectField);
		//
		// TextField<String> secondSubjectField = new TextField<>("second
		// subject");
		// secondSubjectField.setRequired(true);
		// form.add(secondSubjectField);
		//
		// TextField<String> thirdSubjectField = new TextField<>("third
		// subject");
		// thirdSubjectField.setRequired(true);
		// form.add(thirdSubjectField);

		/*
		 * TextField<Double> basePriceField = new TextField<>("basePrice");
		 * basePriceField.add(RangeValidator.<Double> range(0d, 1_000_000d));
		 * basePriceField.setRequired(true); form.add(basePriceField);
		 */
		//
		// CheckBox activeField = new CheckBox("active");
		// form.add(activeField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				enroleeService.saveOrUpdate(enrolee);
				setResponsePage(new HomePage());
			}
		});

		add(new FeedbackPanel("feedback"));

		add(new Link("cancel") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
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