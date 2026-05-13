package com.tripify.info.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

class RequestContextResolverTest {

    private final RequestContextResolver resolver = new RequestContextResolver(new RequestActorResolver());

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void resolvesAuthActorAndRequestId() {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject("user-42")
                .build();
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(jwt, null));

        RequestContext context = resolver.resolve("anon-fallback", "req-1");

        assertThat(context.requestId()).isEqualTo("req-1");
        assertThat(context.actor().type()).isEqualTo(RequestActorType.AUTH);
        assertThat(context.actor().actorId()).isEqualTo("user-42");
    }

    @Test
    void requiresRequestIdHeader() {
        assertThatThrownBy(() -> resolver.resolve("anon-123", " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("X-Request-Id");
    }
}
