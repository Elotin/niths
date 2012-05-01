package no.niths.application.rest;

import static org.junit.Assert.assertEquals;
import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.application.rest.school.RoleController;
import no.niths.application.rest.school.interfaces.CommitteeController;
import no.niths.application.rest.school.interfaces.CourseController;
import no.niths.application.rest.school.interfaces.FadderGroupController;
import no.niths.application.rest.school.interfaces.StudentController;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.school.Committee;
import no.niths.domain.school.Course;
import no.niths.domain.school.FadderGroup;
import no.niths.domain.school.Role;
import no.niths.domain.school.Student;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
public class CascadeControllerTest {

    private MockHttpServletResponse res;

	@Autowired
	private StudentController studController;

	@Autowired
	private CourseController courseController;
	
	@Autowired
	private CommitteeController committeeController;
	
	@Autowired
	private FadderGroupController fadderGroupController;
	
	@Autowired
	private RoleController roleController;

	@Before
	public void setUp() {
	    res = new MockHttpServletResponse();
	}
	
	@Test
	public void testCascadeOperationsOnStudent(){
		int studSize;
		try {
			studSize = studController.getAll(null).size();
		}catch (ObjectNotFoundException e){
			studSize = 0; //Empty db
		}
		//Create a students and persist
		Student s1 = new Student("mail1@nith.no");
		studController.create(s1, res);
		assertEquals(studSize + 1, studController.getAll(null).size());
		
		//Add some courses to the student
		Course c = new Course();
		courseController.create(c, res);
		s1.getCourses().add(c);
		studController.update(s1);
		assertEquals(1, studController.getById(s1.getId()).getCourses().size());
		
		//Add student  to fadderGroup as leader and child
		FadderGroup g = new FadderGroup(99);
		g.getLeaders().add(s1);
		g.getFadderChildren().add(s1);
		fadderGroupController.create(g, res);
		//Add student to a committee as leader
		Committee com = new Committee("Super","asd");
		com.getLeaders().add(s1);
		committeeController.create(com, res);
		//Add as a member
		s1.getCommittees().add(com);
		
		//Add some roles
		Role role = new Role("ROLE_SOME");
		roleController.create(role, res);
		s1.getRoles().add(role);
		
		studController.update(s1);
		//Delete the student
		studController.delete(s1.getId());
		//Course should still be in db
		assertEquals(c, courseController.getById(c.getId()));
		
		FadderGroup g2 = fadderGroupController.getById(g.getId());
		assertEquals(false, g2.getLeaders().contains(s1));
		Committee com2 = committeeController.getById(com.getId());
		assertEquals(false, com2.getLeaders().contains(s1));
		
		committeeController.delete(com.getId());
		courseController.delete(c.getId());
		fadderGroupController.delete(g.getId());
		roleController.delete(role.getId());
		
	}
	

}
