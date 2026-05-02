package com.tripify.hotels.service.kafka.model;

import java.util.List;

public record RefundRuleDto(
        Boolean refundPrepayment,
        List<RefundConditionDto> conditions
) {
}
