package by.training.lysiuk.project.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.training.lysiuk.project.datamodel.PlanSet;

public class PlanSetChoiceRenderer extends ChoiceRenderer<PlanSet> {

	public static final PlanSetChoiceRenderer INSTANCE = new PlanSetChoiceRenderer();

	private PlanSetChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(PlanSet object) {
		return object.getFaculty().getName();
	}

	@Override
	public String getIdValue(PlanSet object, int index) {
		return String.valueOf(object.getId());
	}

}
