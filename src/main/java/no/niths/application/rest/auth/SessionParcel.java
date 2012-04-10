package no.niths.application.rest.auth;

import no.niths.domain.Student;
import no.niths.security.SessionToken;

/**
 * 
 * @author NITHs
 *
 */
public class SessionParcel {

    private Student authenticatedStudent;

    private SessionToken sessionToken;

    public SessionParcel(
            Student authenticatedStudent,
            SessionToken sessionToken) {
        this.authenticatedStudent = authenticatedStudent;
        this.sessionToken         = sessionToken;
    }

    public Student getAuthenticatedStudent() {
        return authenticatedStudent;
    }

    public void setAuthenticatedStudent(Student authenticatedStudent) {
        this.authenticatedStudent = authenticatedStudent;
    }

    public SessionToken getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(SessionToken sessionToken) {
        this.sessionToken = sessionToken;
    }
}