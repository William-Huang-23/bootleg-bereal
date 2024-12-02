package com.william.bootleg_bereal.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

public class AuthenticationService {
    public static Authentication getAuthentication(HttpServletRequest request, Environment environment) {
        String apiKey = request.getHeader(environment.getProperty("api.apiKeyHeaderName"));

        if (apiKey == null || !apiKey.equals(environment.getProperty("api.apiKeyHeaderContent"))) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES);
    }
}
