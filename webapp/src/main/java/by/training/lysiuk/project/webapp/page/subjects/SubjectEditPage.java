package by.training.lysiuk.project.webapp.page.subjects;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.service.SubjectService;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.faculties.FacultiesPage;

public class SubjectEditPage extends AbstractPage {

	@Inject
	private SubjectService subjectService;

	private Subject subject;

	public SubjectEditPage(PageParameters parameters) {
		super(parameters);
	}

	public SubjectEditPage(Subject subject) {
		super();
		this.subject = subject;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<Subject>(subject));
		add(form);

		TextField<String> nameField = new TextField<>("name");
		nameField.setRequired(true);
		nameField.add(new UniqueNameValidator());
		form.add(nameField);

		/*
		 * TextField<Double> basePriceField = new TextField<>("basePrice");
		 * basePriceField.add(RangeValidator.<Double> range(0d, 1_000_000d));
		 * basePriceField.setRequired(true);
		 * 
		 * DateTextField createdField = new DateTextField("created");
		 * createdField.add(new DatePicker()); createdField.setRequired(true);
		 * form.add(createdField);
		 * 
		 * form.add(basePriceField);
		 * 
		 * CheckBox activeField = new CheckBox("active"); form.add(activeField);
		 */
		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				subjectService.saveOrUpdate(subject);
				setResponsePage(new SubjectsPage());
			}
		});

		add(new FeedbackPanel("feedback"));

		add(new Link("cancel") {

			@Override
			public void onClick() {
				setResponsePage(new SubjectsPage());
			}
		});

	}

	private class UniqueNameValidator implements IValidator<String> {

		@Override
		public void validate(IValidatable<String> validatable) {

			Subject subject = subjectService.getByName(validatable.getValue());
			if (subject != null) {
				ValidationError error = new ValidationError();
				error.setMessage(getClass().getSimpleName() + " already exists");
				validatable.error(error);
			}
		}

	}

}