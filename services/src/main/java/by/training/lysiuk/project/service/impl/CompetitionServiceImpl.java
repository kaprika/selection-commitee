package by.training.lysiuk.project.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.datamodel.Competition;
import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.PlanSet;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;
import by.training.lysiuk.project.service.CompetitionService;
import by.training.lysiuk.project.service.EnroleeService;
import by.training.lysiuk.project.service.PlanSetService;
import by.training.lysiuk.project.service.ScoresInSubjectsService;

@Service
public class CompetitionServiceImpl implements CompetitionService {

	@Inject
	private PlanSetService planSetService;

	@Inject
	private ScoresInSubjectsService scoresInSubjectsService;

	@Inject
	private EnroleeService enroleeService;

	@Override
	public List<Competition> createCompetitionList() {
		List<Competition> competitionList = new ArrayList<>();
		List<PlanSet> planSetList = planSetService.getByCurrentDate(new PlanSetFilter());
		for (PlanSet planSet : planSetList) {

			Faculty faculty = planSet.getFaculty();

			Long currentAmount = enroleeService.countByDateAndFaculty(faculty);
			List<Enrolee> enrolees = enroleeService.getEnroleesByPlanSet(planSet);

			List<ScoresInSubjects> scoresInSubjectsList = scoresInSubjectsService.getByDateAndFaculty(faculty);
			for (Enrolee enrolee : enrolees) {
				enrolee.setTotalScore(enrolee.getCertificate());
				for (ScoresInSubjects scoresInSubjects : scoresInSubjectsList) {
					if (scoresInSubjects.getEnrolee().getId().equals(enrolee.getId())) {
						enrolee.setTotalScore(enrolee.getTotalScore() + scoresInSubjects.getPoints());
					}
				}
			}
			enrolees.sort(new Comparator<Enrolee>() {
				@Override
				public int compare(Enrolee a, Enrolee b) {
					return -a.getTotalScore().compareTo(b.getTotalScore());
				}
			});

			Competition competition = new Competition();
			competition.setFaculty(planSet.getFaculty());
			competition.setPlan(planSet.getPlan());
			competition.setCurrentAmount(currentAmount);
			competition.setEnrolees(enrolees);
			competition.setCompetition((double) (competition.getCurrentAmount()) / competition.getPlan());
			competition.setStartDateSet(planSet.getStartDateSet());
			competition.setEndDateSet(planSet.getEndDateSet());

			competitionList.add(competition);
			int passingScore;
			if (enrolees.isEmpty()) {
				passingScore = 0;
			} else if (enrolees.size() < competition.getPlan()) {
				passingScore = enrolees.get(enrolees.size() - 1).getTotalScore();
			} else {
				passingScore = enrolees.get(competition.getPlan() - 1).getTotalScore();
			}
			competition.setPassingScore(passingScore);
		}
		return competitionList;
	}

}
