package com.jeeplus.common.utils.http;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by Le on 2017/11/28.
 */
public class NSAuthenticator extends Authenticator {
    private String user;
    private String password;

    public NSAuthenticator(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return this.password != null?new PasswordAuthentication(this.user, this.password.toCharArray()):new PasswordAuthentication(this.user, "".toCharArray());
    }

    public RequestorType getRequestorType() {
        return RequestorType.PROXY;
    }
}

