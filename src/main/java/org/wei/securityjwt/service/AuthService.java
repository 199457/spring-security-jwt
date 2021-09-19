package org.wei.securityjwt.service;

import org.wei.securityjwt.utils.Result;
import org.wei.securityjwt.vo.AuthRequest;

public interface AuthService {
    Result<Object> Login(AuthRequest request);
}
