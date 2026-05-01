package com.tripify.tickets.service.service.currency;

import com.tripify.tickets.service.model.unified.Currency;
import com.tripify.tickets.service.model.unified.Price;
import com.tripify.tickets.service.model.unified.UnifiedOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketOfferCurrencyService {

    private final CurrencyConversionService currencyConversion;

    public List<UnifiedOffer> normalizeToStorage(List<UnifiedOffer> offers) {
        return offers.stream().map(this::normalizeToStorage).toList();
    }

    public UnifiedOffer normalizeToStorage(UnifiedOffer offer) {
        if (offer == null || offer.price() == null) {
            return offer;
        }

        return offer.toBuilder()
                .price(toStoragePrice(offer.price()))
                .build();
    }

    public long toStorageAmount(long amount, String sourceCurrency) {
        return currencyConversion
                .toStorageCurrency(BigDecimal.valueOf(amount), sourceCurrency)
                .longValue();
    }

    public long fromStorageAmount(long amountInStorageCurrency, String targetCurrency) {
        return currencyConversion
                .fromStorageCurrency(BigDecimal.valueOf(amountInStorageCurrency), targetCurrency)
                .longValue();
    }

    public String storageCurrencyCode() {
        return currencyConversion.storageCurrency();
    }

    private Price toStoragePrice(Price price) {
        String sourceCurrency = price.currency().getCode();
        if (currencyConversion.storageCurrency().equals(sourceCurrency)) {
            return price;
        }

        long amountInStorage = toStorageAmount(price.amount(), sourceCurrency);
        Long originalAmount = price.originalAmount() != null ? price.originalAmount() : price.amount();
        Currency originalCurrency = price.originalCurrency() != null ? price.originalCurrency() : price.currency();

        return Price.builder()
                .amount(amountInStorage)
                .currency(Currency.RUB)
                .originalAmount(originalAmount)
                .originalCurrency(originalCurrency)
                .build();
    }
}
