package com.tripify.info.security;

public record RequestActor(
        String actorId,
        RequestActorType type
) {
}
