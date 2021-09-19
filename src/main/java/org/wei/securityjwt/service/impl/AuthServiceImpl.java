package org.wei.securityjwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.wei.securityjwt.entity.User;
import org.wei.securityjwt.security.jwt.JwtTokenProvider;
import org.wei.securityjwt.service.AuthService;
import org.wei.securityjwt.service.UserService;
import org.wei.securityjwt.utils.RedisUtil;
import org.wei.securityjwt.utils.Result;
import org.wei.securityjwt.vo.AuthRequest;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public Result<Object> Login(AuthRequest request) {
        // 获取用户名
        String username = request.getUsername();
        String password = request.getPassword();
        // 查找用户
        User user = userService.findUserByUsername(username);
        // 判断用户是否存在
        if (user == null) {
            return Result.error("用户不存在");
        }
        // 判断密码是否正确
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Result.error("密码错误");
        }
        // 认证
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // 认证通过 颁发 token
        String token = jwtTokenProvider.createToken(username, authenticate.getAuthorities());
        // 存储 token

        return null;
    }
}
