package org.wei.securityjwt.security.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JwtProperties {

    private String secretKey = "jennie";
    private Long expTime = 3600L;

}
