package no.niths.application.rest;

import static org.junit.Assert.assertEquals;
import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.application.rest.interfaces.RoleController;
import no.niths.application.rest.interfaces.StudentController;
import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.Student;
import no.niths.domain.security.Role;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
public class RoleControllerTest {
	
	@Autowired
	private RoleController roleController;
	
	@Autowired
	private StudentController studenntController;
	
	@Test
	public void testAddAndRemoveRolesToAndFromStudent(){
		Student stud = new Student("stud@nith.no");
		studenntController.create(stud);
		
		Student fetched = studenntController.getById(stud.getId());
		assertEquals(stud, fetched);
		
		assertEquals(true, fetched.getRoles().isEmpty());
		
		int size = 0;
		try{
			size = roleController.getAll(null).size();
		}catch(ObjectNotFoundException e){
			//size = 0;
		}
		
		//Test of add
		Role role = new Role();
		role.setRoleName("ROLE_TEST");
		
		roleController.create(role);
		
		assertEquals(size + 1, roleController.getAll(null).size());
		
		roleController.addStudentRole(stud.getId(), role.getId());
		
		stud = studenntController.getById(stud.getId());
		assertEquals(1, stud.getRoles().size());
		
		Role role2 = new Role();
		role.setRoleName("ROLE_TEST2");
		roleController.create(role2);
		
		roleController.addStudentRole(stud.getId(), role2.getId());
		
		stud = studenntController.getById(stud.getId());
		assertEquals(2, stud.getRoles().size());
		
		//Test of remove
		roleController.removeStudentRole(stud.getId(), role.getId());
		stud = studenntController.getById(stud.getId());
		assertEquals(1, stud.getRoles().size());
		
		roleController.removeAllRolesFromStudent(stud.getId());
		stud = studenntController.getById(stud.getId());
		assertEquals(true, stud.getRoles().isEmpty());

		
	}

}
