package com.tripify.pack.service.exception;

import java.util.UUID;

public class PackNotFoundException extends RuntimeException {

    private final UUID packId;

    public PackNotFoundException(UUID packId) {
        super("Pack not found: " + packId);
        this.packId = packId;
    }

    public UUID packId() {
        return packId;
    }
}
