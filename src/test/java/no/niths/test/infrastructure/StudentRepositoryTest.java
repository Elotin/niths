package no.niths.test.infrastructure;
import static org.junit.Assert.assertEquals;
import no.niths.common.config.HibernateConfig;
import no.niths.domain.Student;
import no.niths.infrastructure.interfaces.StudentRepository;
import no.niths.test.common.config.TestAppConfig;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= { TestAppConfig.class, HibernateConfig.class}, loader = AnnotationConfigContextLoader.class)
//@Transactional
public class StudentRepositoryTest {

	@Autowired
	private StudentRepository studentRepo;

//	@Autowired
//	private CoursesRepository courseRepo;

	/**
	 * Testing of basic CRUD functions
	 */
	@Test
	@Rollback(true)
	public void testCRUD(){
		Student stud = new Student("John", "Doe");
		studentRepo.create(stud);
		
		assertEquals(stud, studentRepo.getById(stud.getId()));
		assertEquals(1, studentRepo.getAllStudents().size());
		
		stud.setFirstName("Jane");
		studentRepo.update(stud);
		
		assertEquals("Jane", studentRepo.getById(stud.getId()).getFirstName());
	
		studentRepo.delete(stud);
		assertEquals(null, studentRepo.getById(stud.getId()));
		
		assertEquals(true, studentRepo.getAllStudents().isEmpty());

	}
	
	@Test
	@Rollback(true)
	public void testC(){
		
	}
	
	

//	@Test
//	public void testGetByName() {
//		//Adding 3 students
//		Student s1 = new Student("John", "Doe");
//		Student s2 = new Student("Mando", "Doe");
//		Student s3 = new Student("Jane", "Doe");
//		
//		getRepo().create(s1);
//		getRepo().create(s2);
//		getRepo().create(s3);
//		
//		assertNotSame(0, getRepo().getAllStudents().size());		
//		assertNotSame(0, getRepo().getByName("").size());		
//		assertNotSame(0, getRepo().getByName("John").size());		
//		assertNotSame(0, getRepo().getByName("Jo").size());		
//		assertNotSame(0, getRepo().getByName("hn").size());		
//		assertEquals(true, getRepo().getByName("non").isEmpty());
//		
//		getRepo().delete(s1);
//		getRepo().delete(s2);
//		getRepo().delete(s3);
//	
//		assertEquals(true, getRepo().getAllStudents().isEmpty());
//		
//	}
//	
//	/**
//	 * Testing the connection between students and courses
//	 */
//	@Test
//	public void testAddAndGetCourses(){
//		//Adds three students
//		Student s1 = new Student("Tom", "Jones");
//		Student s2 = new Student("Jane", "Jones");
//		//Adds two courses
//		Course c1 = new Course("Prog", "desc");
//		Course c2 = new Course("AA", "desc");
//		//Adds two courses to the first student
//		s1.getCourses().add(c1);
//		s1.getCourses().add(c2);
//	
//		//Add the students to DB
//		getRepo().create(s1);
//		getRepo().create(s2);
//		
//		//Courses should be persisted
//		assertEquals(2, getCourseRepo().getAll().size());
//
//		//Student should have two courses
//		assertEquals(2, s1.getCourses().size());
//		
//		//Removes a course
//		s1.getCourses().remove(1);
//		getRepo().update(s1);
//		
//		//Courses should still exist
//		assertEquals(2, getCourseRepo().getAll().size());
//				
//		//Student should have one courses
//		assertEquals(1, s1.getCourses().size());
//		
//		getRepo().delete(s1);
//		getRepo().delete(s2);
//		
//		getCourseRepo().delete(c1);
//		getCourseRepo().delete(c2);
//		
//		assertEquals(true, getRepo().getAllStudents().isEmpty());
//		assertEquals(true, getCourseRepo().getAll().isEmpty());
//		
//		
//	}
//
//	@Test
//	public void testJoinCourses(){
//		// 3 students
//		Student testStudent = new Student("Jonh", "Doe");
//		Student aStudent = new Student("Kevin", "Smith");
//		Student bStudent = new Student("Baker", "Doe");
//		testStudent.setId(getRepo().create(testStudent));
//		// creates a student without courses
//		bStudent.setId(getRepo().create(bStudent));
//		
//	
//
//		//3 courses
//		final String COURSE_NAME = "Progammering";
//		Course c1 = new Course(COURSE_NAME, "cool");
//		Course c2 = new Course("Spill prog", "cool");
//		Course c3 = new Course("Spill design", "cool");
//		
//		// create a course explicit
//		c3.setId(getCourseRepo().create(c3));
//		
//		// add the other two courses to the test student
//		List<Course> cList = new ArrayList<Course>();
//		cList.add(c1);
//		cList.add(c2);
//		testStudent.setCourses(cList);
//	
//		// updates the student and the courses should be created as well 
//		getRepo().update(testStudent);
//		
//		cList.clear();
//		cList = courseRepo.getAll();
//
//		// add all three courses to aStudentd
//		aStudent.setId(getRepo().create(aStudent));
//		aStudent.setCourses(cList);
//		getRepo().update(aStudent);
//		
//		
//		// retrieves students with courses
//		List<Student> c = getRepo().getStudentsWithCourses();
//		assertEquals(2,c.size());
//		
//		assertEquals(2,c.get(0).getCourses().size());
//		
//		c.clear();
//		c = getRepo().getByStudentsNamedCourse(COURSE_NAME);
//		
//		assertEquals(2,c.size());
//		
//		//Clean  up
//		getRepo().delete(testStudent);
//		getRepo().delete(aStudent);
//		getRepo().delete(bStudent);
//		getCourseRepo().delete(c1);
//		getCourseRepo().delete(c2);
//		getCourseRepo().delete(c3);
//		
//		assertEquals(true, getRepo().getAllStudents().isEmpty());
//		assertEquals(true, getCourseRepo().getAll().isEmpty());
//	}
//	
//	
//	@Test
//	public void testJoinCommittees(){
//		
//		Student testStudent = new Student("Jonh", "Doe");
//		Student stud = new Student("Broiler", "MR");
//		
//		testStudent.setId(getRepo().create(testStudent));
//		getRepo().create(stud);
//		
//		
//		Committee lug = new Committee("LUG","linux utvalg");
//		Committee mdf = new Committee("MDF","MDF is cool");
//		
//		List<Committee> committeeList = new ArrayList<Committee>();
//		committeeList.add(lug);
//		committeeList.add(mdf);
//		testStudent.setCommittees(committeeList);
//		
//		getRepo().update(testStudent);
//			
////		List<Student> c = studentRepo.getStudentsWithCommittees();
//		// one students with 
////		assertEquals(1,c.size());
//		
////		assertEquals(2,c.get(0).getCommittees().size());
//		getRepo().delete(testStudent);
//		getRepo().delete(stud);
//	}
//	
//	
//	
//	public StudentRepository getRepo() {
//		return studentRepo;
//	}
//	
//	public CoursesRepository getCourseRepo() {
//		return courseRepo;
//	}

}