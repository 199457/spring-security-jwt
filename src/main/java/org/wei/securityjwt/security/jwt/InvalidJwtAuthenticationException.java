package org.wei.securityjwt.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    /**
     * jwt 校验异常
     */
    private static final long serialVersionUID = -761503632186596312L;

    public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}