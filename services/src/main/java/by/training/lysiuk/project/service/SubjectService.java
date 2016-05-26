package by.training.lysiuk.project.service;

import java.util.List;

import javax.transaction.Transactional;

import by.training.lysiuk.project.dataaccess.filters.SubjectFilter;
import by.training.lysiuk.project.datamodel.Subject;

public interface SubjectService {
	Subject getSubject(Long id);

	@Transactional
	void saveOrUpdate(Subject subject);

	@Transactional
	void delete(Long id);

	@Transactional
	void delete(Subject subject);

	List<Subject> getAll();

	Long count(SubjectFilter filter);

	List<Subject> find(SubjectFilter filter);

	Subject getByName(String subjectName);

}
