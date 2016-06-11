package by.training.lysiuk.project.dataaccess;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.ScoresInSubjectsFilter;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;

public interface ScoresInSubjectsDao extends AbstractDao<ScoresInSubjects, Long>  {

	public List<ScoresInSubjects> find(ScoresInSubjectsFilter filter);
}
