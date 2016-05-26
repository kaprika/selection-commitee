package by.training.lysiuk.project.service;

import java.util.List;
import javax.transaction.Transactional;

import by.training.lysiuk.project.dataaccess.filters.FacultyFilter;
import by.training.lysiuk.project.datamodel.Faculty;

public interface FacultyService {

	Faculty getFaculty(Long id);

	@Transactional
	void saveOrUpdate(Faculty faculty);

	@Transactional
	void delete(Long id);
	
	@Transactional
	void delete(Faculty faculty);

	List<Faculty> getAll();

	Long count(FacultyFilter filter);

	List<Faculty> find(FacultyFilter filter);
	
	Faculty getByName(String facultyName);

}
