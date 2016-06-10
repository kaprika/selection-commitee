package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.EnrolleeFilter;
import by.training.lysiuk.project.datamodel.Enrolee;

public interface EnroleeDao extends AbstractDao<Enrolee, Long> {

	Long count(EnrolleeFilter filter);

	List<Enrolee> find(EnrolleeFilter filter);

}
