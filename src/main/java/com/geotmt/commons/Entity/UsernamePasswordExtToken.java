package com.geotmt.commons.Entity;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * Created by geo on 2018/9/13.
 */
public class UsernamePasswordExtToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    private String username;
    private char[] password;
    private boolean rememberMe;
    private String host;
    private String apiToken;

    public UsernamePasswordExtToken() {
        this.rememberMe = false;
    }

    public UsernamePasswordExtToken(String username, char[] password) {
        this(username, (char[])password, false, (String)null);
    }

    public UsernamePasswordExtToken(String username, String password) {
        this(username, (char[])(password != null?password.toCharArray():null), false, (String)null);
    }

    public UsernamePasswordExtToken(String username, char[] password, String host) {
        this(username, password, false, host);
    }

    public UsernamePasswordExtToken(String username, String password, String host) {
        this(username, password != null?password.toCharArray():null, false, host);
    }

    public UsernamePasswordExtToken(String username, char[] password, boolean rememberMe) {
        this(username, (char[])password, rememberMe, (String)null);
    }

    public UsernamePasswordExtToken(String username, String password, boolean rememberMe) {
        this(username, (char[])(password != null?password.toCharArray():null), rememberMe, (String)null);
    }

    public UsernamePasswordExtToken(String username, char[] password, boolean rememberMe, String host) {
        this.rememberMe = false;
        this.username = username;
        this.password = password;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public UsernamePasswordExtToken(String username, String password, boolean rememberMe, String host) {
        this(username, password != null?password.toCharArray():null, rememberMe, host);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return this.password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public Object getPrincipal() {
        return this.getUsername();
    }

    public Object getCredentials() {
        return this.getPassword();
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public void clear() {
        this.username = null;
        this.host = null;
        this.rememberMe = false;
        if(this.password != null) {
            for(int i = 0; i < this.password.length; ++i) {
                this.password[i] = 0;
            }

            this.password = null;
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append(" - ");
        sb.append(this.username);
        sb.append(", rememberMe=").append(this.rememberMe);
        if(this.host != null) {
            sb.append(" (").append(this.host).append(")");
        }

        return sb.toString();
    }
}
