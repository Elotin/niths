package no.niths.application.rest;

import java.util.List;

import javax.validation.Valid;

import no.niths.common.AppConstants;
import no.niths.common.RESTConstants;
import no.niths.domain.Course;
import no.niths.domain.CourseList;
import no.niths.services.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(AppConstants.COURSES)
public class CourseController {

    @Autowired
    private CourseService service;

    /**
     * 
     * @param Course The course to be created
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createCourse(@RequestBody Course course) {
        service.createCourse(course);
    }

    /**
     * 
     * @param String The course's id
     * @return The course identified by the id
     */
    @RequestMapping(
            value    = {"{id}.json", "id/{id}.json"},
            method   = RequestMethod.GET,
            produces = RESTConstants.JSON)
    @ResponseBody()
    public Course getCourseByIdAsJSON(@PathVariable long id) {
        return service.getCourseById(id);
    }

    @RequestMapping(
            value    = {"{id}.xml", "id/{id}.xml"},
            method   = RequestMethod.GET,
            produces = RESTConstants.XML)
    @ResponseBody
    public Course getCourseByIdAsXML(@PathVariable long id) {
        return service.getCourseById(id);
    }

    @RequestMapping(
            value    = "name/{name}.json",
            method   = RequestMethod.GET,
            produces = RESTConstants.JSON)
    @ResponseBody
    public Course getCourseByNameAsJSON(@PathVariable String name) {
        return service.getCourseByName(name);
    }

    @RequestMapping(
            value    = "name/{name}.xml",
            method   = RequestMethod.GET,
            produces = RESTConstants.XML)
    @ResponseBody
    public Course getCourseByNameAsXML(@PathVariable String name) {
        return service.getCourseByName(name);
    }

    @RequestMapping(
            value    = {"", "all.json"},
            method   = RequestMethod.GET,
            produces = RESTConstants.JSON)
    @ResponseBody
    public List<Course> getAllCoursesAsJSON() {
        return service.getAllCourses();
    }

    @RequestMapping(
            value    = "all.xml",
            method   = RequestMethod.GET,
            produces = RESTConstants.XML)
    @ResponseBody
    public CourseList getAllCoursesAsXML() {
        CourseList list = new CourseList();
        list.setData(service.getAllCourses());
        
        return list;
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCourse(Course course) {
        service.updateCourse(course);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCourse(Course course) {
        service.deleteCourse(course);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteCourse(String id) {
    }
}