package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.SubjectFilter;
import by.training.lysiuk.project.datamodel.Subject;

public interface SubjectDao extends AbstractDao<Subject, Long> {
	Long count(SubjectFilter filter);

	List<Subject> find(SubjectFilter filter);
}
