package no.niths.services;

import java.util.List;

import no.niths.domain.Course;
import no.niths.infrastructure.interfaces.CoursesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CoursesRepository repo;

    public Long createCourse(Course course) {
        return repo.createCourse(course);
    }

    public Course getCourseById(long id){
        return repo.getCourseById(id);
   }

    public Course getCourseByName(String name) {
        return repo.getCourseByName(name);
    }

    public List<Course> getAllCourses(){
        return repo.getAllCourses();
    }

    public void updateCourse(Course course) {
        repo.updateCourse(course);
    }

    public void deleteCourse(Course course) {
    }
}