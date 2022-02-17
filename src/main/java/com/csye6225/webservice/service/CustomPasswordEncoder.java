package com.csye6225.webservice.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {
    String salt = "$2a$10$llw0G6IyibUob8h5XRt9xuRczaGdCm/AiV6SSjf5v78XS824EGbh";

    @Override
    public String encode(CharSequence rawPassword) {
        BCrypt bCryptb = new BCrypt();
        String hasedPassword = bCryptb.hashpw(rawPassword.toString(), salt);
        return hasedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String actualEncodedPasseord = encode(rawPassword);
        return actualEncodedPasseord.equals(encodedPassword);
    }
}
