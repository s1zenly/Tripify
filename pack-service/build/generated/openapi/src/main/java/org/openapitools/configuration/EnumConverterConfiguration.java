package org.openapitools.configuration;

import com.tripify.pack.generated.model.CabinClass;
import com.tripify.pack.generated.model.Currency;
import com.tripify.pack.generated.model.GenerationMode;
import com.tripify.pack.generated.model.JourneyType;
import com.tripify.pack.generated.model.PackErrorCode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

/**
 * This class provides Spring Converter beans for the enum models in the OpenAPI specification.
 *
 * By default, Spring only converts primitive types to enums using Enum::valueOf, which can prevent
 * correct conversion if the OpenAPI specification is using an `enumPropertyNaming` other than
 * `original` or the specification has an integer enum.
 */
@Configuration(value = "org.openapitools.configuration.enumConverterConfiguration")
public class EnumConverterConfiguration {

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.cabinClassConverter")
    Converter<String, CabinClass> cabinClassConverter() {
        return new Converter<String, CabinClass>() {
            @Override
            public CabinClass convert(String source) {
                return CabinClass.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.currencyConverter")
    Converter<String, Currency> currencyConverter() {
        return new Converter<String, Currency>() {
            @Override
            public Currency convert(String source) {
                return Currency.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.generationModeConverter")
    Converter<String, GenerationMode> generationModeConverter() {
        return new Converter<String, GenerationMode>() {
            @Override
            public GenerationMode convert(String source) {
                return GenerationMode.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.journeyTypeConverter")
    Converter<String, JourneyType> journeyTypeConverter() {
        return new Converter<String, JourneyType>() {
            @Override
            public JourneyType convert(String source) {
                return JourneyType.fromValue(source);
            }
        };
    }
    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.packErrorCodeConverter")
    Converter<String, PackErrorCode> packErrorCodeConverter() {
        return new Converter<String, PackErrorCode>() {
            @Override
            public PackErrorCode convert(String source) {
                return PackErrorCode.fromValue(source);
            }
        };
    }

}
