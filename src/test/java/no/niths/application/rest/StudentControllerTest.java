package no.niths.application.rest;

import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolationException;

import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.application.rest.interfaces.StudentController;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
public class StudentControllerTest {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentControllerTest.class);

	@Autowired
	private StudentController studController;

	@Test(expected= ConstraintViolationException.class)
	public void testInsertNullObject_shallThrowException() {
		
		Student s = new Student();
		studController.create(s);
	}
	
	@Test
	public void testCreateAndDeleteWithExistingIds(){
		int size = 0;
		try {
			size = studController.getAll(null).size();
		} catch (ObjectNotFoundException e) {
			//size = 0
		}
		
		Student s = new Student("mail@mail.com");
		studController.create(s);
		
		assertEquals(size + 1, studController.getAll(null).size());
		
		studController.hibernateDelete(s.getId());
		int currentSize = 0;
		try {
			currentSize  = studController.getAll(null).size();
		} catch (ObjectNotFoundException e) {
			//size = 0
		}
		
		assertEquals(size, currentSize);
		
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void testDeleteWithUnvalidId(){
		studController.hibernateDelete(new Long(9391));
	}
	
	@Test
	public void testGetMethods(){
		int size = 0;
		try {
			size = studController.getAll(null).size();
		} catch (ObjectNotFoundException e) {
			//size = 0
		}
		Student s1 = new Student("mail1@mail.com");
		Student s2 = new Student("mail2@mail.com");
		Student s3 = new Student("mail3@mail.com");
		
		studController.create(s1);
		studController.create(s2);
		studController.create(s3);
		
		assertEquals(size + 3, studController.getAll(null).size());
		
		assertEquals(s1, studController.getById(s1.getId()));
		
		assertEquals(1, studController.getAll(s1).size());
		
		s1.setEmail("xxx@mail.com");
		assertEquals("mail1@mail.com", studController.getById(s1.getId()).getEmail());
		
		studController.update(s1);
		assertEquals("xxx@mail.com", studController.getById(s1.getId()).getEmail());
	}
	

}
