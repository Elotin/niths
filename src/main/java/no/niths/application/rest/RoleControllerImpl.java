package no.niths.application.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import no.niths.application.rest.interfaces.RoleController;
import no.niths.application.rest.lists.ListAdapter;
import no.niths.application.rest.lists.RoleList;
import no.niths.common.AppConstants;
import no.niths.common.ValidationHelper;
import no.niths.domain.Student;
import no.niths.domain.security.Role;
import no.niths.services.interfaces.GenericService;
import no.niths.services.interfaces.RoleService;
import no.niths.services.interfaces.StudentService;

@Controller
@RequestMapping(AppConstants.ROLES)
public class RoleControllerImpl extends AbstractRESTControllerImpl<Role> implements RoleController{

	private static final Logger logger = LoggerFactory
			.getLogger(RoleControllerImpl.class);

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private StudentService studentService;
	
	private RoleList roleList = new RoleList();
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = { 
			"setRole/{studentId}/{roleId}" }, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED, reason = "Role added")
	public void addStudentRole(@PathVariable Long studentId, @PathVariable Long roleId) {
		Student stud = studentService.getById(studentId);
		ValidationHelper.isObjectNull(stud);
		
		Role role = roleService.getById(roleId);
		ValidationHelper.isObjectNull(role);
		
		stud.getRoles().add(role);
		studentService.update(stud);
		
		logger.debug("Added role to student: " + role.getRoleName());
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	@RequestMapping(value = { "removeRole/{studentId}/{roleId}" }, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK, reason = "Role removed")
	public void removeStudentRole(Long studId, Long roleId) {
		Student stud = studentService.getById(studId);
		ValidationHelper.isObjectNull(stud);
		
		Role role = roleService.getById(roleId);
		ValidationHelper.isObjectNull(role);
		
		stud.getRoles().remove(role);
		studentService.update(stud);
		
		logger.debug("Removed role from student: " + role.getRoleName());
	}
	
	
	@Override
	@RequestMapping(value = { "removeRoles/{studentId}" }, method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK, reason = "Roles removed from student")
	public void removeAllRolesFromStudent(Long studId) {
		Student stud = studentService.getById(studId);
		ValidationHelper.isObjectNull(stud);
		
		stud.getRoles().clear();
		studentService.update(stud);
		
		logger.debug("All roles removed from student");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericService<Role> getService() {
		return roleService;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListAdapter<Role> getList() {
		return roleList;
	}


	
}
