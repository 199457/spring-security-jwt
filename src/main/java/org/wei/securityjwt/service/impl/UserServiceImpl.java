package org.wei.securityjwt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.wei.securityjwt.entity.User;
import org.wei.securityjwt.mapper.UserMapper;
import org.wei.securityjwt.security.SecurityUser;
import org.wei.securityjwt.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    
    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username);
        return new SecurityUser(
                user.getUsername(),
                user.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole())
        );
    }
}
