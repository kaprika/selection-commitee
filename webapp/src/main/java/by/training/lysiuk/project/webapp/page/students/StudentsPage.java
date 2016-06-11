package by.training.lysiuk.project.webapp.page.students;

import by.training.lysiuk.project.webapp.page.AbstractPage;
import by.training.lysiuk.project.webapp.page.students.panel.StudentListPanel;

public class StudentsPage extends AbstractPage {

	public StudentsPage() {
		super();
		add(new StudentListPanel("list-panel"));

	}
}