package com.tripify.info.security;

import org.springframework.stereotype.Component;

@Component
public class RequestContextResolver {

    private final RequestActorResolver requestActorResolver;

    public RequestContextResolver(RequestActorResolver requestActorResolver) {
        this.requestActorResolver = requestActorResolver;
    }

    public RequestContext resolve(String xAnonymousId, String xRequestId) {
        if (xRequestId == null || xRequestId.isBlank()) {
            throw new IllegalArgumentException("X-Request-Id header is required");
        }

        RequestActor actor = requestActorResolver.resolve(xAnonymousId);
        return new RequestContext(actor, xRequestId.trim());
    }
}
