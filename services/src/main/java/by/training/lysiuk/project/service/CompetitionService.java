package by.training.lysiuk.project.service;

import java.util.List;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Competition;
import by.training.lysiuk.project.datamodel.PlanSet;

public interface CompetitionService {

	 List<Competition> createCompetitionList(List<PlanSet> planSetList);
	 
	 List<Competition> competitionListByCurrentDate();
	 
	 List<Competition> closedCompetitionList(PlanSetFilter filter);
}
