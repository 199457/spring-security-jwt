package org.wei.securityjwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.wei.securityjwt.entity.User;

public interface UserService {
    
    User findUserByUsername(String username);

    UserDetails loadUserByUsername(String username);
}
