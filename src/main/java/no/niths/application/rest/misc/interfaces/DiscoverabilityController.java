package no.niths.application.rest.misc.interfaces;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import no.niths.application.rest.misc.RestResource;

/**
 * Simple discoverability class that handles GET requests to the ROOT uri
 * <p>
 * Returns all valid domain in header
 *
 */
public interface DiscoverabilityController {
	/**
	 * Returns all domains
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    String root(HttpServletRequest request, HttpServletResponse response);

    /**
     * 
     * Returns a list of all services in the API.
     * <p>
     * Contains path, method type, path variables,
     * status messages, status code, headers and
     * authorization
     * </p>
     * @param req the http header, specifies return format
     * @return a list of resources in the API
     */
	List<RestResource> getApi(HttpServletRequest req);

	/**
	 * Returns a HTML view of the API
	 * 
	 * @param req the http header
	 * @return html view of services in the API
	 */
	ModelAndView getApiExcel(HttpServletRequest req);
}