package no.niths.application.rest.interfaces;

import no.niths.application.rest.exception.ObjectNotFoundException;
import no.niths.domain.Application;
/**
 * Controller for handling applications
 *
 */
public interface ApplicationController extends GenericRESTController<Application> {

	/**
	 * Enables an application
	 * <p>
	 * Applications must be enabled to do request
	 * <p>
	 * @param applicationId id of the application
	 * @throws ObjectNotFoundException if no application is found
	 */
	void enableApplication(Long applicationId);

}
