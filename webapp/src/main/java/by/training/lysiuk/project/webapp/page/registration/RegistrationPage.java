package by.training.lysiuk.project.webapp.page.registration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.service.EnroleeService;
import by.training.lysiuk.project.service.PlanSetService;
import by.training.lysiuk.project.webapp.common.events.PlanSetChangeEvent;
import by.training.lysiuk.project.webapp.common.renderer.PlanSetChoiceRenderer;
import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.home.HomePage;

public class RegistrationPage extends AbstractPage {

	@Inject
	private PlanSetService planSetService;

	@Inject
	private EnroleeService enroleeService;

	private Enrolee enrolee;

	private List<ScoresInSubjects> scoresInSubjects;

	public RegistrationPage(PageParameters parameters) {
		super(parameters);
	}

	public RegistrationPage(Enrolee enrolee) {
		super();
		this.enrolee = enrolee;
		scoresInSubjects = new ArrayList<>();
		scoresInSubjects.add(new ScoresInSubjects());
		scoresInSubjects.add(new ScoresInSubjects());
		scoresInSubjects.add(new ScoresInSubjects());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<Enrolee>(enrolee));
		add(form);

		TextField<String> lastNameField = new TextField<>("lastName");
		lastNameField.setLabel(new ResourceModel("table.last name"));
		lastNameField.setRequired(true);
		form.add(lastNameField);
		TextField<String> firstNameField = new TextField<>("firstName");
		firstNameField.setLabel(new ResourceModel("table.first name"));
		firstNameField.setRequired(true);
		form.add(firstNameField);

		TextField<String> identificationNumberField = new TextField<>("identificationNumber");
		identificationNumberField.setLabel(new ResourceModel("table.identification number"));
		identificationNumberField.setRequired(true);
		identificationNumberField.add(new UniqueIdentificatorNumberValidator());
		form.add(identificationNumberField);

		TextField<String> phoneNumberField = new TextField<>("phoneNumber");
		phoneNumberField.setLabel(new ResourceModel("table.phone number"));
		phoneNumberField.setRequired(true);
		phoneNumberField.add(new PhoneNumberValidator());
		form.add(phoneNumberField);

		TextField<String> emailField = new TextField<>("email");
		emailField.setLabel(new ResourceModel("table.email"));
		emailField.setRequired(true);
		emailField.add(new EmailValidator());
		form.add(emailField);

		Label firstSubject = new Label("subject1", new Model<String>()) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof PlanSetChangeEvent) {
					this.setVisible(true);
				}
			}
		};
		firstSubject.setOutputMarkupId(true);
		firstSubject.setOutputMarkupPlaceholderTag(true);
		firstSubject.setVisible(false);
		form.add(firstSubject);

		Label secondSubject = new Label("subject2", new Model<String>()) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof PlanSetChangeEvent) {
					this.setVisible(true);
				}
			}
		};
		secondSubject.setOutputMarkupId(true);
		secondSubject.setOutputMarkupPlaceholderTag(true);
		form.add(secondSubject);

		Label thirdSubject = new Label("subject3", new Model<String>()) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof PlanSetChangeEvent) {
					this.setVisible(true);
				}
			}
		};
		thirdSubject.setOutputMarkupId(true);
		thirdSubject.setOutputMarkupPlaceholderTag(true);
		form.add(thirdSubject);

		ScoresInSubjects firstPoints = scoresInSubjects.get(0);
		TextField<Integer> firstScoresField = new TextField<Integer>("first points",
				new PropertyModel<>(firstPoints, "points")) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof PlanSetChangeEvent) {
					this.setVisible(true);
				}
			}
		};
		firstScoresField.add(RangeValidator.<Integer> range(0, 100));
		firstScoresField.setLabel(new ResourceModel("table.score1"));
		firstScoresField.setRequired(true);
		firstScoresField.setVisible(false);
		firstScoresField.setOutputMarkupPlaceholderTag(true);
		firstScoresField.setOutputMarkupId(true);
		form.add(firstScoresField);

		ScoresInSubjects secondPoints = scoresInSubjects.get(1);
		TextField<Integer> secondScoresField = new TextField<Integer>("second points",
				new PropertyModel<>(secondPoints, "points")) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof PlanSetChangeEvent) {
					this.setVisible(true);
				}
			}
		};
		secondScoresField.add(RangeValidator.<Integer> range(0, 100));
		secondScoresField.setLabel(new ResourceModel("table.score2"));
		secondScoresField.setRequired(true);
		secondScoresField.setVisible(false);
		secondScoresField.setOutputMarkupPlaceholderTag(true);
		secondScoresField.setOutputMarkupId(true);
		form.add(secondScoresField);

		ScoresInSubjects thirdPoints = scoresInSubjects.get(2);
		TextField<Integer> thirdScoresField = new TextField<Integer>("third points",
				new PropertyModel<>(thirdPoints, "points")) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof PlanSetChangeEvent) {
					this.setVisible(true);
				}
			}
		};
		thirdScoresField.add(RangeValidator.<Integer> range(0, 100));
		thirdScoresField.setLabel(new ResourceModel("table.score3"));
		thirdScoresField.setRequired(true);
		thirdScoresField.setVisible(false);
		thirdScoresField.setOutputMarkupPlaceholderTag(true);
		thirdScoresField.setOutputMarkupId(true);
		form.add(thirdScoresField);

		final DropDownChoice<PlanSet> dropDownChoice = new DropDownChoice<PlanSet>("planSet",
				planSetService.getByCurrentDate(new PlanSetFilter()), PlanSetChoiceRenderer.INSTANCE);
		dropDownChoice.setLabel(new ResourceModel("table.faculty"));
		dropDownChoice.setRequired(true);
		form.add(dropDownChoice);

		dropDownChoice.add(new AjaxFormComponentUpdatingBehavior("change") {
			@Override
			protected void onUpdate(final AjaxRequestTarget target) {
				List<Subject> subjects = dropDownChoice.getModelObject().getSubjects();
				for (int i = 0; i < 3; i++) {
					scoresInSubjects.get(i).setSubject(subjects.get(i));
				}
				String firstSubjectName = scoresInSubjects.get(0).getSubject().getName();
				firstSubject.setDefaultModelObject(firstSubjectName);
				String secondSubjectName = scoresInSubjects.get(1).getSubject().getName();
				secondSubject.setDefaultModelObject(secondSubjectName);
				String thirdSubjectName = scoresInSubjects.get(2).getSubject().getName();
				thirdSubject.setDefaultModelObject(thirdSubjectName);
				send(getPage(), Broadcast.BREADTH, new PlanSetChangeEvent());
				target.add(firstSubject, secondSubject, thirdSubject, firstScoresField, secondScoresField,
						thirdScoresField);
			}
		});

		TextField<Integer> certificateField = new TextField<>("certificate");
		certificateField.add(RangeValidator.<Integer> range(0, 100));
		certificateField.setLabel(new ResourceModel("table.certificate"));
		certificateField.setRequired(true);
		form.add(certificateField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();

				scoresInSubjects.get(0).setEnrolee(enrolee);
				scoresInSubjects.get(1).setEnrolee(enrolee);
				scoresInSubjects.get(2).setEnrolee(enrolee);
				enrolee.setDateOfRegistration(new Date());
				enroleeService.saveOrUpdateEnroleeWithPoints(enrolee, scoresInSubjects);
				HomePage page = new HomePage();
				page.info(getString("registration.success"));
				setResponsePage(page);
			}
		});

		add(new FeedbackPanel("feedback"));

		form.add(new Link("cancel") {
			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});

	}

	private class UniqueIdentificatorNumberValidator implements IValidator<String> {

		@Override
		public void validate(IValidatable<String> validatable) {

			Enrolee enrolee = enroleeService.getByIdentificationNumber(validatable.getValue());
			if (enrolee != null) {
				ValidationError error = new ValidationError();
				error.setMessage(getString("registration.err"));
				validatable.error(error);
			}
		}

	}

	private class EmailValidator implements IValidator<String> {

		@Override
		public void validate(IValidatable<String> validatable) {
			String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
			Pattern p = Pattern.compile(ePattern);
			Matcher m = p.matcher(validatable.getValue());
			if (!m.matches()) {
				ValidationError error = new ValidationError();
				error.setMessage(getString("email.err"));
				validatable.error(error);
			}
		}
	}

	private class PhoneNumberValidator implements IValidator<String> {

		@Override
		public void validate(IValidatable<String> validatable) {
			String phonePattern = "^\\+375-[1-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2}$";
			Pattern p = Pattern.compile(phonePattern);
			Matcher m = p.matcher(validatable.getValue());
			if (!m.matches()) {
				ValidationError error = new ValidationError();
				error.setMessage(getString("phone.err"));
				validatable.error(error);
			}
		}
	}

}