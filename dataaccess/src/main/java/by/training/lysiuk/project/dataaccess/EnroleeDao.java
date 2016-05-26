package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.EnroleeFilter;
import by.training.lysiuk.project.datamodel.Enrolee;

public interface EnroleeDao extends AbstractDao<Enrolee, Long> {

	Long count(EnroleeFilter filter);

	List<Enrolee> find(EnroleeFilter filter);

}
