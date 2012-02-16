package no.niths.infrastructure.interfaces;

import java.util.List;

import no.niths.domain.Student;


public interface StudentRepository extends GenericRepository<Student>{

	List<Student> getStudentsWithNamedCourse(String name);
}
