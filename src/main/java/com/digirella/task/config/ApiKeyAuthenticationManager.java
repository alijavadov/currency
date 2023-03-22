package com.digirella.task.config;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.digirella.task.filter.ApiKeyAuthenticationFilter.API_KEY_HEADER;
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyAuthenticationManager implements AuthenticationManager {
    private String apiToken;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String providedApiKey = (String) authentication.getCredentials();

        if (StringUtils.hasText(providedApiKey)) {
            if (providedApiKey.equals(apiToken)) {
                var roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
                return new UsernamePasswordAuthenticationToken(API_KEY_HEADER, null, List.of(roleAdmin));
            }
        }

        return authentication;
    }
}
