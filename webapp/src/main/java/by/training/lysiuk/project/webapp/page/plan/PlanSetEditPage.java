package by.training.lysiuk.project.webapp.page.plan;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.markup.html.form.palette.theme.DefaultTheme;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.RangeValidator;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.Subject;
import by.training.lysiuk.project.service.EnroleeService;
import by.training.lysiuk.project.service.FacultyService;
import by.training.lysiuk.project.service.PlanSetService;
import by.training.lysiuk.project.service.SubjectService;
import by.training.lysiuk.project.webapp.common.renderer.FacultyChoiceRenderer;
import by.training.lysiuk.project.webapp.common.renderer.SubjectChoiceRenderer;
import by.training.lysiuk.project.webapp.page.AbstractPage;

public class PlanSetEditPage extends AbstractPage {

	@Inject
	private PlanSetService planSetService;

	@Inject
	private SubjectService subjectService;

	@Inject
	private FacultyService facultyService;

	@Inject
	private EnroleeService enroleeService;

	private PlanSet planSet;

	private boolean isEnabled;

	private boolean isEnabledPlan;

	private boolean createNew;

	private PlanSetFilter filter;

	public PlanSetEditPage(PageParameters parameters) {
		super(parameters);
	}

	public PlanSetEditPage(PlanSet planSet, boolean createNew) {
		super();
		this.planSet = planSet;
		this.createNew = createNew;
		filter = new PlanSetFilter();
		if (createNew) {
			isEnabled = true;
			isEnabledPlan = true;

		} else {
			boolean noEnrolees = enroleeService.getEnroleesByPlanSet(planSet).isEmpty();
			boolean planSetIsActive = planSet.getEndDateSet().after(new Date());
			if (noEnrolees && planSetIsActive) {
				isEnabled = true;
			} else {
				isEnabled = false;
			}
			isEnabledPlan = planSetIsActive;
		}

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form form = new Form("form", new CompoundPropertyModel<PlanSet>(planSet));
		add(form);

		DateTextField startDateSetField = new DateTextField("startDateSet");
		startDateSetField.add(new DatePicker());
		startDateSetField.setRequired(true);
		startDateSetField.setLabel(new ResourceModel("table.start date set"));
		startDateSetField.setEnabled(isEnabled);
		form.add(startDateSetField);

		DropDownChoice<Faculty> facultyField = new DropDownChoice<Faculty>("faculty", facultyService.getAll(),
				FacultyChoiceRenderer.INSTANCE);
		facultyField.setRequired(true);
		facultyField.setLabel(new ResourceModel("table.faculty"));
		facultyField.setEnabled(isEnabled);
		form.add(facultyField);

		TextField<Integer> planField = new TextField<>("plan");
		planField.add(RangeValidator.<Integer> range(1, 1_000));
		planField.setRequired(true);
		planField.setLabel(new ResourceModel("table.plan"));
		planField.setEnabled(isEnabledPlan);
		form.add(planField);

		DateTextField endDateSetField = new DateTextField("endDateSet");
		endDateSetField.add(new DatePicker());
		endDateSetField.setRequired(true);
		endDateSetField.setLabel(new ResourceModel("table.end date set"));
		endDateSetField.setEnabled(isEnabled);
		form.add(endDateSetField);

		List<Subject> allSubjects = subjectService.getAll();
		final Palette<Subject> palette = new Palette<Subject>("subjects", Model.ofList(planSet.getSubjects()),
				new CollectionModel<Subject>(allSubjects), SubjectChoiceRenderer.INSTANCE, 15, false, true);
		palette.add(new DefaultTheme());
		palette.setRequired(true);
		palette.add(new SubjectsQuantityValidator());
		palette.setLabel(new ResourceModel("table.subjects"));
		palette.setEnabled(isEnabled);
		form.add(palette);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				if (planSet.getStartDateSet().equals(planSet.getEndDateSet())
						|| planSet.getStartDateSet().after(planSet.getEndDateSet())) {
					PlanSetEditPage page = new PlanSetEditPage(planSet, createNew);
					page.warn(getString("planSet.date error"));
					setResponsePage(page);
				} else {
					String faculty = planSet.getFaculty().getName();
					filter.setFacultyName(faculty);
					if (planSetService.countByYearAndFaculty(filter) != 0 && createNew) {
						PlanSetEditPage page = new PlanSetEditPage(planSet, true);
						page.warn(getString("planSet.exists"));
						setResponsePage(page);
					} else {
						planSetService.saveOrUpdate(planSet);
						setResponsePage(new PlanSetPage());
					}
				}
			}
		});

		add(new FeedbackPanel("feedback"));

		form.add(new Link("cancel") {
			@Override
			public void onClick() {
				setResponsePage(new PlanSetPage());
			}
		});

	}

	private class SubjectsQuantityValidator implements IValidator<Collection<Subject>> {

		@Override
		public void validate(IValidatable<Collection<Subject>> validatable) {
			int subjectsQuantity = validatable.getValue().size();
			if (subjectsQuantity != 3) {
				ValidationError error = new ValidationError();
				error.setMessage(getString("planSet.err"));
				validatable.error(error);
			}
		}

	}
}