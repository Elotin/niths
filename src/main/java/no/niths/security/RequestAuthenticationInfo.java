package no.niths.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
/**
 * 
 * Wrapper class for HTTP headers we want to authenticate
 * 
 */
public class RequestAuthenticationInfo extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1192605098301148314L;

    private RequestHolderDetails principal;
    private String developerToken;
    private String sessionToken;
    private String appToken;
    private String appKey;
    private String developerKey;

    public RequestAuthenticationInfo(RequestHolderDetails principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }
    
    public RequestAuthenticationInfo(){
        super(null);
        setAuthenticated(false);
    }

    public RequestAuthenticationInfo(RequestHolderDetails principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    public RequestHolderDetails getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    public String getDeveloperToken() {
        return developerToken;
    }

    public void setDeveloperToken(String developerToken) {
        this.developerToken = developerToken;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        //credentials = null;
    }
    @Override
    public Object getCredentials() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getDeveloperKey() {
        return developerKey;
    }

    public void setDeveloperKey(String developerKey) {
        this.developerKey = developerKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
