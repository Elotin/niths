package no.niths.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import no.niths.common.config.HibernateConfig;
import no.niths.common.config.TestAppConfig;
import no.niths.domain.Committee;
import no.niths.domain.Course;
import no.niths.domain.Student;
import no.niths.domain.security.Role;
import no.niths.infrastructure.interfaces.CommitteeRepositorty;
import no.niths.infrastructure.interfaces.CourseRepository;
import no.niths.infrastructure.interfaces.RoleRepository;
import no.niths.infrastructure.interfaces.StudentRepository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestAppConfig.class, HibernateConfig.class })
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback=true) 
public class StudentRepositoryTest {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentRepositoryTest.class); // Replace with test class

	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private CommitteeRepositorty comRepo;
	
	@Autowired
	private RoleRepository roleRepo;

	/**
	 * Testing of basic CRUD functions
	 */
	@Test
	public void testCRUD() {
		int size = studentRepo.getAll(null).size();
		Student stud = new Student("John", "Doe");
		stud.setEmail("mail@mail.com");
		studentRepo.create(stud);

		assertEquals(stud, studentRepo.getById(stud.getId()));
		assertEquals(size + 1, studentRepo.getAll(null).size());

		stud.setFirstName("Jane");
		studentRepo.update(stud);

		assertEquals("Jane", studentRepo.getById(stud.getId()).getFirstName());

		studentRepo.delete(stud.getId());
		// assertEquals(null, studentRepo.getById(stud.getId()));

		assertEquals(size, studentRepo.getAll(null).size());

	}

	/**
	 * Tests the course association
	 */
	@Test
	public void testStudentWithCourses() {
		int cSize = courseRepo.getAll(null).size();
		int sSize = studentRepo.getAll(null).size();
		int comSize = comRepo.getAll(null).size();
		
		Course c1 = new Course("PROG", "casd1");
		Course c2 = new Course("DESIGN", "cdda2");

		courseRepo.create(c1);
		courseRepo.create(c2);
		// Courses should be persisted
		assertEquals(cSize + 2, courseRepo.getAll(null).size());

		Student stud = new Student("John", "Doe");
		stud.setEmail("mail@mail.com");
		stud.getCourses().add(c1);
		stud.getCourses().add(c2);

		studentRepo.create(stud);
		assertEquals(stud, studentRepo.getById(stud.getId()));
		// Test if student has courses
		assertEquals(2, studentRepo.getById(stud.getId()).getCourses().size());

		studentRepo.getById(stud.getId()).getCourses().remove(1);
		assertEquals(1, studentRepo.getById(stud.getId()).getCourses().size());

		assertEquals(cSize + 2, courseRepo.getAll(null).size());

		Committee c = new Committee("Utvalg", "desc");

		comRepo.create(c);
		assertEquals(comSize + 1, comRepo.getAll(null).size());
		stud = studentRepo.getById(stud.getId());
		stud.getCommittees().add(c);

		studentRepo.update(stud);

		assertEquals(1, studentRepo.getById(stud.getId()).getCommittees()
				.size());

	}

	@Ignore
	@Test
	public void testGetAllStudentsWithParameter_shouldReturnListOfStudentsMatching() {
		
		int size = studentRepo.getAll(null).size();
		createStudentHelper();
		assertEquals(size + 4, studentRepo.getAll(null).size());

		Student toFind = new Student("John", "Doe");
		assertEquals(2, studentRepo.getAll(toFind).size());

		toFind = new Student("Jane", "Doe");
		assertEquals(1, studentRepo.getAll(toFind).size());

		toFind = new Student("XXX", "Doe");
		assertEquals(0, studentRepo.getAll(toFind).size());
	
	}

	@Ignore
	@Test
	public void testGetStudentsWithNamedCourse(){
		int size = studentRepo.getAll(null).size();
		List<Student> students =createStudentHelper();
		assertEquals(size + 4, studentRepo.getAll(null).size());
		
		String name ="prog";
		Course c = new Course(name, "ProgProg");
		courseRepo.create(c);
		
		ArrayList<Course> cs = new ArrayList<Course>();
		cs.add(c);
		
		for (int i = 0; i < students.size()-1; i++) {
			students.get(i).setCourses(cs);
			studentRepo.update(students.get(i));
		}
		
		students.clear();
		students = studentRepo.getStudentsWithNamedCourse(name);
		
		assertEquals(3, students.size());
		
	}
	
//	@Test
//	public void testGetStudentBySessionToken(){
//		Student s1 = new Student("mail@nith.no");
//		s1.setSessionToken("aToken");
//		studentRepo.create(s1);
//		Student s2 = new Student("mail2@nith.no");
//		s2.setSessionToken("bToken");
//		studentRepo.create(s2);
//		
//		assertEquals(s1, studentRepo.getStudentBySessionToken("aToken"));
//		assertEquals(null, studentRepo.getStudentBySessionToken("cToken"));
//	}
	
	private ArrayList<Student>createStudentHelper(){
		ArrayList<Student> students = new ArrayList<Student>();
		
		String [] firstName = {"John","John","Jane","Foo"};
		String [] lastName = {"Doe","Doe","Doe","Bar"};
		
		for (int i = 0; i < lastName.length; i++) {
			Student s1 = new Student(firstName[i],lastName[i]);
			s1.setEmail("mail" + i + "@mail.com");
			studentRepo.create(s1);
			students.add(s1);
		}
		return students;
	}
	
	
	
	@Test
	public void testAddRoles(){
		Student s1 = new Student("J","D");
		s1.setEmail("mailai@mail.com");
		studentRepo.create(s1);
		
		long [] arr = {1,2};
		
		List<Role> roles = roleRepo.getAll(null);
		s1.getRoles().clear();
		for(Role r: roles){
			
			for(long l: arr){
				if(r.getId() == l){
					s1.getRoles().add(r);
				}
			}
		
		}
		studentRepo.update(s1);
		
		s1 = studentRepo.getById(s1.getId());
		
		assertNotNull(s1);
		
		assertEquals(2, s1.getRoles().size());
	}
}
