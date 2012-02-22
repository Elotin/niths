package no.niths.common;

import java.util.ArrayList;

import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.domain.Committee;
import no.niths.domain.Student;

public class ValidationHelper {

	/**
	 * @Param committee
	 */
	public static boolean isObjectNull(Object obj) {
		if (obj == null) {
			throw new ObjectNotFoundException();
		}
		return false;
	}


	/**
	 * 
	 * @param committee
	 * @param studentLeader
	 */
	public static boolean isStudentLeaderInCommittee(Committee committee,
			Student studentLeader) {
		isObjectNull(committee);
		if (!(committee.getLeaders().contains(studentLeader))) {
			throw new ObjectNotFoundException();
		}
		return false;
	}

	public static boolean isListEmpty(
			@SuppressWarnings("rawtypes") ArrayList list) {

		if (list.isEmpty()) {
			throw new ObjectNotFoundException();
		}
		return false;
	}
}