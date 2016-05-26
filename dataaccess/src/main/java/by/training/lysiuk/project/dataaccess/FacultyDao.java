package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.FacultyFilter;
import by.training.lysiuk.project.datamodel.Faculty;

public interface FacultyDao extends AbstractDao<Faculty, Long> {
	
	Long count(FacultyFilter filter);

	List<Faculty> find(FacultyFilter filter);
}
