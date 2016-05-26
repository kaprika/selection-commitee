package by.training.lysiuk.project.service;

import java.lang.reflect.Field;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.training.lysiuk.project.dataaccess.FacultyDao;
import by.training.lysiuk.project.dataaccess.impl.AbstractDaoImpl;
import by.training.lysiuk.project.datamodel.Faculty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-context-test.xml" })
public class FacultyServiceTest {

	@Inject
	private FacultyService facultyService;
	
	@Inject
	private FacultyDao facultyDao;

	@Test
	public void test() {
		Assert.assertNotNull(facultyService);
	}

	
	@Test
	public void testEntityManagerInitialization()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = AbstractDaoImpl.class.getDeclaredField("entityManager");
		f.setAccessible(true);
		EntityManager em = (EntityManager) f.get(facultyDao);

		Assert.assertNotNull(em);
	}
	
	@Test
	public void testSearchByName(){
		Faculty f = new Faculty();
		Faculty faculty = facultyService.getByName("math");
		Assert.assertNotNull(faculty);
	}
	/*
	@Test
    public void testRegistration() {
        Faculty faculty = new Faculty();
       // UserCredentials userCredentials = new UserCredentials();

        faculty.setName("testNam10");
        
        facultyService.insert(faculty);
        Faculty insertedFaculty = facultyService.getFaculty(faculty.getId());

        Assert.assertNotNull(insertedFaculty);

        String updatedName = "updatedName12";
        faculty.setName(updatedName);
        facultyService.update(faculty);

        Assert.assertEquals(updatedName, facultyService.getFaculty(faculty.getId()).getName());
*/
        //userService.delete(profile.getId());

       // Assert.assertNull(userService.getProfile(profile.getId()));
      /*
        Assert.assertNotNull(registredProfile);
        Assert.assertNotNull(registredCredentials);

        String updatedFName = "updatedName";
        profile.setFirstName(updatedFName);
        userService.update(profile);

        Assert.assertEquals(updatedFName, userService.getProfile(profile.getId()).getFirstName());

        userService.delete(profile.getId());

        Assert.assertNull(userService.getProfile(profile.getId()));
        Assert.assertNull(userService.getCredentials(userCredentials.getId()));*/
	
    //}

	 
}
