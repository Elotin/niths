package no.niths.application.rest;

import java.util.ArrayList;
import java.util.List;

import no.niths.application.rest.exception.DuplicateEntryCollectionException;
import no.niths.application.rest.exception.NotInCollectionException;
import no.niths.application.rest.interfaces.CommitteeController;
import no.niths.application.rest.lists.CommitteeList;
import no.niths.application.rest.lists.ListAdapter;
import no.niths.common.AppConstants;
import no.niths.common.SecurityConstants;
import no.niths.common.ValidationHelper;
import no.niths.domain.Committee;
import no.niths.domain.Event;
import no.niths.domain.Student;
import no.niths.services.interfaces.CommitteeService;
import no.niths.services.interfaces.EventsService;
import no.niths.services.interfaces.GenericService;
import no.niths.services.interfaces.StudentService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class provides CRUD actions in RESTstyle <br />
 * 
 * Mapping :<br />
 * host:port/committees<br />
 * 
 * Headers : <br />
 * Accept:application/json<br />
 * Content-Type:appilcation/json <br />
 * Accept:application/xml<br />
 * Content-Type:appilcation/xml<br />
 * 
 */
@Controller
@RequestMapping(AppConstants.COMMITTEES)
public class CommitteeControllerImpl extends
		AbstractRESTControllerImpl<Committee> implements CommitteeController {

	@Autowired
	private CommitteeService committeeService;

	@Autowired
	private EventsService eventService;

	@Autowired
	private StudentService studentService;

	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(CommitteeControllerImpl.class);

	private CommitteeList committeeList = new CommitteeList();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Committee getById(@PathVariable Long id) {
		logger.debug(id + "");
		Committee committee = super.getById(id);

		if (committee != null) {
			List<Student> leaders = committee.getLeaders();

			for (Student leader : leaders) {
				leader.setCommittees(null);
				leader.setCourses(null);
				leader.setFeeds(null);
				// leaders.get(i).setFadderGroup(null);
			}
			List<Student> members = committee.getMembers();
			for (Student member : members) {
				member.setCommittees(null);
				member.setCourses(null);
				member.setFeeds(null);
			}

			for (Event event : committee.getEvents()) {
				event.setLocation(null);
			}
		}
		return committee;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Committee> getAll(Committee domain) {
		committeeList = (CommitteeList) super.getAll(domain);
		clearRelations();
		return committeeList;
	}

	@Override
	public ArrayList<Committee> getAll(Committee domain,
			@PathVariable int firstResult, @PathVariable int maxResults) {
		committeeList = (CommitteeList) super.getAll(domain, firstResult,
				maxResults);
		clearRelations();
		return committeeList;
	}

	private void clearRelations() {
		for (Committee committee : committeeList) {
			committee.setEvents(null);
			committee.setLeaders(null);
			committee.setMembers(null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	public void hibernateDelete(@PathVariable long id) {
		super.hibernateDelete(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	public void create(@RequestBody Committee domain) {
		super.create(domain);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	public void update(@RequestBody Committee domain) {
		super.update(domain);
	}

	/**
	 * Adds a leader to a committee
	 * 
	 * @param committeeId
	 *            The id of the committee
	 * @param studentId
	 *            The id of the student to add as leader
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	@RequestMapping(value = "add/leader/{committeeId}/{studentId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK, reason = "Leader added to committee")
	public void addLeader(@PathVariable Long committeeId,
			@PathVariable Long studentId) {
		Committee committee = committeeService.getById(committeeId);
		ValidationHelper.isObjectNull(committee, "Committee not found");
		Student student = studentService.getById(studentId);
		ValidationHelper.isObjectNull(student, "Student not found");
		if (committee.getLeaders().contains(student)) {
			throw new DuplicateEntryCollectionException(
					"Student already a leader");
		}
		committee.getLeaders().add(student);
		committeeService.update(committee);
	}

	/**
	 * Removes a leader from a committee
	 * 
	 * @param committeeId
	 *            The id of the committee to remove leader from
	 * @param studentId
	 *            The id of the student to remove
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	@RequestMapping(value = "remove/leader/{committeeId}/{studentId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK, reason = "Deleted")
	public void removeLeader(@PathVariable Long committeeId,
			@PathVariable Long studentId) {
		Committee committee = committeeService.getById(committeeId);
		ValidationHelper.isObjectNull(committee, "Committee not found");

		boolean isRemoved = false;
		for (int i = 0; i < committee.getLeaders().size(); i++) {
			if (committee.getLeaders().get(i).getId() == studentId) {
				committee.getLeaders().remove(i);
				isRemoved = true;
				break;
			}
		}

		if (isRemoved) {
			committeeService.update(committee);
		} else {
			throw new NotInCollectionException("The student is not a leader");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	@RequestMapping(value = "add/event/{committeeId}/{eventId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK, reason = "Event added")
	public void addEvent(@PathVariable Long committeeId,
			@PathVariable Long eventId) {
		Committee committee = committeeService.getById(committeeId);
		ValidationHelper.isObjectNull(committee, "Committee not found");

		Event event = eventService.getById(eventId);
		ValidationHelper.isObjectNull(event, "Event not found");

		if (committee.getEvents().contains(event)) {
			throw new DuplicateEntryCollectionException("Event already added");
		}

		if (committee.getEvents().add(event)) {
			committeeService.update(committee);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@PreAuthorize(SecurityConstants.ADMIN_SR_COMMITTEE_LEADER)
	@RequestMapping(value = "remove/event/{committeeId}/{eventId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK, reason = "Event removed")
	public void removeEvent(@PathVariable Long committeeId,
			@PathVariable Long eventId) {
		Committee committee = committeeService.getById(committeeId);
		ValidationHelper.isObjectNull(committee, "Committee not found");

		boolean isRemoved = false;

		for (int i = 0; i < committee.getEvents().size(); i++) {
			if (committee.getEvents().get(i).getId() == eventId) {
				committee.getEvents().remove(i);
				isRemoved = true;
				break;
			}
		}

		if (isRemoved) {
			committeeService.update(committee);
		} else {
			throw new NotInCollectionException(
					"Committe does not have that event");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GenericService<Committee> getService() {
		return committeeService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ListAdapter<Committee> getList() {
		return committeeList;
	}
}
