package no.niths.infrastructure.interfaces;

import no.niths.domain.APIEvent;

/**
 * Repository class for APIEvents
 * To persist the event,  annotate any public method with @ApiEvent(title = "title).
 * 
 * The method must be public and take one parameter.
 * 
 * How to use:
 * @ApiEvent(title="Something happened")
 * public void anyMethod(Object obj){}
 * 
 *
 */
public interface APIEventRepository extends GenericRepository<APIEvent> {

}