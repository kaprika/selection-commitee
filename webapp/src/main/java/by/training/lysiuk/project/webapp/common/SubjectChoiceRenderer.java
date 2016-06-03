package by.training.lysiuk.project.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.training.lysiuk.project.datamodel.Subject;

public class SubjectChoiceRenderer extends ChoiceRenderer<Subject> {

	public static final SubjectChoiceRenderer INSTANCE = new SubjectChoiceRenderer();

	private SubjectChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Subject object) {
		return object.getName();
	}

	@Override
	public String getIdValue(Subject object, int index) {
		return String.valueOf(object.getId());
	}

}
