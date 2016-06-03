package by.training.lysiuk.project.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.custom.Competition;
import by.training.lysiuk.project.service.custom.CompetitionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class CompetitionServiceTest {

	@Inject
	private CompetitionService competitionService;

	@Inject
	private PlanSetService planSetService;

	@Inject
	private ScoresInSubjectsService scoresInSubjectsService;

	@Inject
	private EnroleeService enroleeService;

	@Test
	public void createCompetitionListTest() {

		List<Competition> competitionList = competitionService.createCompetitionList();

		for (Competition competition : competitionList) {
			System.out.println(competition.getFaculty().getName());
			List<Enrolee> enrolees = competition.getEnrolees();
			for (Enrolee enrolee : enrolees) {
				System.out
						.println(enrolee.getFirstName() + " " + enrolee.getLastName() + " " + enrolee.getTotalScore());
			}

		}

	}

}
