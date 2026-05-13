package com.tripify.info.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

class RequestActorResolverTest {

    private final RequestActorResolver resolver = new RequestActorResolver();

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void resolvesAuthActorFromJwtSubject() {
        Jwt jwt = Jwt.withTokenValue("token")
                .header("alg", "none")
                .subject("user-42")
                .build();
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(jwt, null));

        RequestActor actor = resolver.resolve("anon-fallback");

        assertThat(actor.type()).isEqualTo(RequestActorType.AUTH);
        assertThat(actor.actorId()).isEqualTo("user-42");
    }

    @Test
    void resolvesAnonymousActorWhenJwtMissing() {
        RequestActor actor = resolver.resolve("anon-123");

        assertThat(actor.type()).isEqualTo(RequestActorType.ANONYMOUS);
        assertThat(actor.actorId()).isEqualTo("anon-123");
    }
}
