package by.training.lysiuk.project.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.training.lysiuk.project.datamodel.Enrolee;
import by.training.lysiuk.project.datamodel.Faculty;
import by.training.lysiuk.project.datamodel.PlanSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class EnroleeServiceTest {
	
	@Inject
	private EnroleeService enroleeService;
	
	@Inject
	private PlanSetService planSetService;

	@Test
	public void test() {
		Assert.assertNotNull(enroleeService);
	}
	
	@Test
	public void countByFacultyTest(){
		Faculty faculty = new Faculty();
		faculty.setName("math");
		System.out.println(enroleeService.countByDateAndFaculty(faculty));
	}

	@Test
	public void getEnroleesByPlanSetTest(){
		PlanSet planSet = planSetService.getPlanSet((long) 3);
		List<Enrolee> enrolees = enroleeService.getEnroleesByPlanSet(planSet);
		for (Enrolee enrolee : enrolees) {
			
		
		System.out.println(enrolee.getLastName() + " " + enrolee.getPlanSet().getFaculty().getName());
		}
	}


}
