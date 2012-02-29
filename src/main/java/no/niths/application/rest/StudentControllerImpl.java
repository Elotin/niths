package no.niths.application.rest;

import java.util.ArrayList;
import java.util.List;

import no.niths.application.rest.interfaces.StudentController;
import no.niths.application.rest.lists.ListAdapter;
import no.niths.application.rest.lists.StudentList;
import no.niths.common.AppConstants;
import no.niths.common.ValidationHelper;
import no.niths.domain.Course;
import no.niths.domain.Student;
import no.niths.services.interfaces.GenericService;
import no.niths.services.interfaces.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(AppConstants.STUDENTS)
public class StudentControllerImpl extends AbstractRESTControllerImpl<Student>
		implements StudentController {

	private static final Logger logger = LoggerFactory
			.getLogger(StudentControllerImpl.class);

	private StudentList studentList = new StudentList();

	@Autowired
	private StudentService service;

	@Override
	@RequestMapping(value = { "{id}" }, method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
	@ResponseBody
	public Student getById(@PathVariable Long id) {
		Student student = super.getById(id);
		if (student != null) {

			for (int i = 0; i < student.getCommittees().size(); i++) {
				student.getCommittees().get(i).setEvents(null);
				student.getCommittees().get(i).setLeaders(null);
			}

			for (int i = 0; i < student.getCourses().size(); i++) {
				student.getCourses().get(i).setSubjects(null);
			}

			student.getFadderUka().size();
		}
		return student;
	}

	@Override
	@RequestMapping(method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
	@ResponseBody
	public ArrayList<Student> getAll(Student domain) {
		studentList = (StudentList) super.getAll(domain);
		for (int i = 0; i < studentList.size(); i++) {
			studentList.get(i).setCommittees(null);
			studentList.get(i).setCourses(null);
			studentList.get(i).setFadderUka(null);
		}
		return studentList;
	}

	/**
	 * {@inheritDoc}
	 */
	@RequestMapping(value = "course", method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
	@ResponseBody
	public List<Student> getStudentsWithNamedCourse(Course course) {
		String name = course.getName();
		logger.info(name);

		renewList(service.getStudentsWithNamedCourse(name));

		for (int i = 0; i < studentList.size(); i++) {
			studentList.get(i).setCommittees(null);
			studentList.get(i).setCourses(null);
			studentList.get(i).setFadderUka(null);
		}
		return studentList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericService<Student> getService() {
		return service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListAdapter<Student> getList() {
		return studentList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = { "{studentId}/orientation-group/{groupId}" }, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED, reason = "Created")
	public void addStudentToFadderUka(@PathVariable long studentId,
			@PathVariable int groupId) {

		logger.info(studentId + " " + groupId);
		Student student = service.getById(studentId);
		ValidationHelper.isObjectNull(student);
		service.addStudentToFadderUka(student, groupId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = "orientation-group", method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
	@ResponseBody
	public List<Student> getAllStudentsInAFadderUka() {
		studentList.clear();
		studentList.addAll(service.getAllStudentsInAFadderUka());
		studentList.setData(studentList); // for xml marshalling
		ValidationHelper.isListEmpty(studentList);

		return studentList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = "orientation-group/{groupId}", method = RequestMethod.GET, headers = RESTConstants.ACCEPT_HEADER)
	@ResponseBody
	public List<Student> getAllStudentsInFadderUkaBelongingToAGroup(
			@PathVariable int groupId) {
		renewList(service.getAllStudentsInFadderUkaBelongingToAGroup(groupId));

		for (int i = 0; i < studentList.size(); i++) {
			studentList.get(i).setCommittees(null);
			studentList.get(i).setCourses(null);
		}

		return studentList;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = "{studentId}/orientation-group/{groupId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK, reason = "Deleted")
	public void removeStudentFromFadderUka(@PathVariable long studentId,
			@PathVariable int groupId) {
		Student student = getById(studentId);
		ValidationHelper.isObjectNull(student);

		service.removeStudentFromFadderUka(student, groupId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = "{studentId}/orientation-group", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.OK, reason = "Deleted")
	public void removeStudentFromAllOfFadderUka(@PathVariable long studentId) {
		Student student = getById(studentId);
		ValidationHelper.isObjectNull(student);
		service.removeStudentFromAllOfFadderUka(student);
	}
}
