package com.tripify.info.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class RequestActorResolver {

    public RequestActor resolve(String xAnonymousId) {
        String userId = extractUserIdOrNull();
        if (userId != null) {
            return new RequestActor(userId, RequestActorType.AUTH);
        }

        if (xAnonymousId == null || xAnonymousId.isBlank()) {
            throw new IllegalArgumentException("X-Anonymous-Id header is required when JWT is absent");
        }

        return new RequestActor(xAnonymousId.trim(), RequestActorType.ANONYMOUS);
    }

    private static String extractUserIdOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getSubject();
    }
}
