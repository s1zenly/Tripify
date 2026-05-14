package com.tripify.pack.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class RequestSubjectResolver {

    private static final Logger log = LoggerFactory.getLogger(RequestSubjectResolver.class);
    private static final String USER_ID_HEADER = "X-User-Id";

    private RequestSubjectResolver() {
    }

    public static String resolveSubjectId(String xAnonymousId) {
        String userId = extractUserIdOrNull();
        return userId != null ? userId : xAnonymousId;
    }

    public static String extractUserIdOrNull() {
        String gatewayUserId = requestHeader(USER_ID_HEADER);
        if (gatewayUserId != null && !gatewayUserId.isBlank()) {
            log.info("X-User-Id propagated by gateway: {}", gatewayUserId.trim());
            return gatewayUserId.trim();
        }
        log.debug("X-User-Id header is absent; falling back to JWT/anonymous");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
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
