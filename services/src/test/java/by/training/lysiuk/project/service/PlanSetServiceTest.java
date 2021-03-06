package by.training.lysiuk.project.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.training.lysiuk.project.dataaccess.PlanSetDao;
import by.training.lysiuk.project.dataaccess.filters.PlanSetFilter;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.PlanSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class PlanSetServiceTest {
	@Inject
	private PlanSetService planSetService;

	@Inject
	private PlanSetDao planSetDao;

	@Test
	public void test() {
		Assert.assertNotNull(planSetService);
	}

	@Test
	public void testEntityManagerInitialization()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = AbstractDaoImpl.class.getDeclaredField("entityManager");
		f.setAccessible(true);
		EntityManager em = (EntityManager) f.get(planSetDao);

		Assert.assertNotNull(em);
	}

	@Test
	public void testCountByYearAndFaculty() {
		Date date = new Date();
		PlanSetFilter filter = new PlanSetFilter();
		filter.setFacultyName("math");
		Long count = planSetService.countByYearAndFaculty(filter);
		// System.out.println(count);
	}

	@Test
	public void testSearch() {

		PlanSetFilter filter = new PlanSetFilter();
		filter.setFetchFaculty(true);
		filter.setFetchSubjects(true);
		List<PlanSet> result = planSetService.find(filter);
		for (PlanSet p : result) {
			if (!p.getSubjects().isEmpty()) {
				// System.out.println(p.getFaculty().getName() + " " +
				// p.getSubjects().get(0).getName() + " "
				// + p.getSubjects().get(1).getName() + " " +
				// p.getSubjects().size());
			}
		}
	}

	@Test
	public void testGetByCurrentYear() {
		PlanSetFilter planSetFilter = new PlanSetFilter();
		Date date = new Date(0);
		List<PlanSet> planSetList = planSetService.getByCurrentYear(planSetFilter);
		for (PlanSet planSet : planSetList) {
			// System.out.println(planSet.getStartDateSet());
		}
		// System.out.println(planSetList.size());
	}

	@Test
	public void GetClosedCompetitionListTest() {

		/*
		 * List<PlanSet> planSeteList =
		 * planSetService.getClosedPlansSet(startDate, endDate); for (PlanSet
		 * planSet : planSeteList) {
		 * System.out.println(planSet.getStartDateSet() + " " +
		 * planSet.getEndDateSet() + " " +planSet.getFaculty().getName());
		 * 
		 * }
		 */
	}

}
