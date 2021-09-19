package org.wei.securityjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.wei.securityjwt.entity.User;

@Component
public interface UserMapper extends BaseMapper<User> {
}
