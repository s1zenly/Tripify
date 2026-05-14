package com.tripify.info.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RequestActorResolver {

    private static final Logger log = LoggerFactory.getLogger(RequestActorResolver.class);
    private static final String USER_ID_HEADER = "X-User-Id";

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
        String gatewayUserId = requestHeader(USER_ID_HEADER);
        if (gatewayUserId != null && !gatewayUserId.isBlank()) {
            log.info("X-User-Id propagated by gateway: {}", gatewayUserId.trim());
            return gatewayUserId.trim();
        }
        log.debug("X-User-Id header is absent; falling back to JWT/anonymous");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            return null;
        }
        return jwt.getSubject();
    }

    private static String requestHeader(String name) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (!(attributes instanceof ServletRequestAttributes servletAttributes)) {
            return null;
        }
        return servletAttributes.getRequest().getHeader(name);
    }
}
