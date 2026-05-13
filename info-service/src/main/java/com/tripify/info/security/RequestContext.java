package com.tripify.info.security;

public record RequestContext(
        RequestActor actor,
        String requestId
) {
}
