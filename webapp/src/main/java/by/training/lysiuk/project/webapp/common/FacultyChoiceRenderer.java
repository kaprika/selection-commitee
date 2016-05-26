package by.training.lysiuk.project.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.model.IModel;

import by.training.lysiuk.project.datamodel.Faculty;

public class FacultyChoiceRenderer extends ChoiceRenderer<Faculty> {

	public static final FacultyChoiceRenderer INSTANCE = new FacultyChoiceRenderer();

	private FacultyChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Faculty object) {
		return object.getName();
	}

	@Override
	public String getIdValue(Faculty object, int index) {
		return String.valueOf(object.getId());
	}

}
