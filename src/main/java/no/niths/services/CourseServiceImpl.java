package no.niths.services;

import java.util.List;

import no.niths.domain.Course;
import no.niths.infrastructure.interfaces.CoursesRepository;
import no.niths.services.interfaces.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService{

    @Autowired
    private CoursesRepository repo;

    public Long create(Course course) {
        return repo.create(course);
    }

    public Course getById(long id) {
    	Course c = repo.getById(id);
    	if(c != null){
    		c.getSubjects().size();
    	}
        return c;
   }
    
    public List<Course> getAll(Course c) {
    	List<Course> results = repo.getAll(c);
    	for (Course cor : results){
    		cor.getSubjects().size();
    	}
        return results;
    }

    public void update(Course course) {
        repo.update(course);
    }

    public boolean delete(long id) {
        return repo.delete(id);
    }

	@Override
	public Course getCourse(String name, Integer grade, String term) {
			
		Course course = repo.getCourse(name, grade, term);
		if(course !=null){
			course.getSubjects().size();
			
		}
		return course;
	}

}