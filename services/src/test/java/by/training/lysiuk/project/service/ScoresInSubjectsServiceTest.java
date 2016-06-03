package by.training.lysiuk.project.service;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.ScoresInSubjects;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class ScoresInSubjectsServiceTest {

	@Inject
	private ScoresInSubjectsService scoresInSubjectsService;

	@Test
	public void test() {
		Assert.assertNotNull(scoresInSubjectsService);
	}

	@Test
	public void getByDateAndFacultyTest() {
		Faculty faculty = new Faculty();
		faculty.setName("phys");
		List<ScoresInSubjects> scoresInSubjectsList = scoresInSubjectsService.getByDateAndFaculty(faculty);
		System.out.println(scoresInSubjectsList.size());
		for (ScoresInSubjects scoresInSubjects : scoresInSubjectsList) {
			System.out.println(scoresInSubjects.getEnrolee().getPlanSet().getFaculty().getName());

		}
	}
	
	
	@Test
	public void getSumPointsByDateAndFacultyTest() {
		Faculty faculty = new Faculty();
		faculty.setName("phys");
		List<Integer> max = scoresInSubjectsService.getSumPointsByDateAndFaculty(faculty);
		for (Integer integer : max) {
			System.out.println(integer);
		}
	}
	
	@Test
	public void getEnroleesListByDateAndFAcultyTest() {
		Faculty faculty = new Faculty();
		faculty.setName("math");
		List<Enrolee> enrolees = scoresInSubjectsService.getEnroleesListByDateAndFAculty(faculty);
		for (Enrolee enrolee : enrolees) {
			System.out.println(enrolee.getLastName()+" " + enrolee.getId());
		}
	}

}
