package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.PlanSet;

public interface PlanSetDao extends AbstractDao<PlanSet, Long> {

	Long count(PlanSetFilter filter);

	List<PlanSet> find(PlanSetFilter filter);
	
}
