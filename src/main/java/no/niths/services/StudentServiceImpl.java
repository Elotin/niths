package no.niths.services;

import java.util.ArrayList;
import java.util.List;

import no.niths.domain.Student;
import no.niths.infrastructure.interfaces.StudentRepository;
import no.niths.services.interfaces.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentServiceImpl.class);

	@Autowired
	private StudentRepository repo;

	public Long create(Student student) {
		return repo.create(student);
	}

	/**
	 * Finds and returns a student with a given id.
	 * Returns the student with courses and committees
	 * 
	 * @param id ID of student to find
	 * @return the student
	 */
	public Student getById(long id) {
		Student s = repo.getById(id);
		if (s != null) {
			if (s.getCommittees().size() > 0) {
				for (int i = 0; i < s.getCommittees().size(); i++) {
					s.getCommittees().get(i).setEvents(null);
					s.getCommittees().get(i).setLeaders(null);
				}
			}else{
				s.setCommittees(null);
			}
			if (s.getCourses().size() > 0) {
				for (int i = 0; i < s.getCourses().size(); i++) {
					s.getCourses().get(i).setTopics(null);
				}
			}else{
				s.setCourses(null);
			}
		}
		return s;
	}

	public List<Student> getAll(Student s) {
		ArrayList<Student> temp = (ArrayList<Student>) repo.getAll(s);
		for (int i = 0; i < temp.size(); i++) {
			// temp.get(i).getCommittees().size();
			// temp.get(i).getCourses().size();
			temp.get(i).setCommittees(null);
			temp.get(i).setCourses(null);
		}
		return temp;
	}

	public void update(Student student) {
		repo.update(student);
	}

	public boolean delete(long id) {
		return repo.delete(id);
	}
	
	public List<Student> getStudentsWithNamedCourse(String name){
		
		ArrayList<Student> temp = (ArrayList<Student>) repo.getStudentsWithNamedCourse(name);
		for (int i = 0; i < temp.size(); i++) {
			temp.get(i).setCommittees(null);
			temp.get(i).setCourses(null);
		}
		return temp;
	}
}
