package org.wei.securityjwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.wei.securityjwt.mapper")
@SpringBootApplication
public class SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityJwtApplication.class, args);
    }
}
