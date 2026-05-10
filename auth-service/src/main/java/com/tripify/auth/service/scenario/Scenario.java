package com.tripify.auth.service.scenario;

public interface Scenario<I, O> {

    O run(I request);
}
