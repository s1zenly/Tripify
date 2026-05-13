package org.openapitools.configuration;

import com.tripify.hotels.generated.model.ErrorCode;
import com.tripify.hotels.generated.model.GenerationMode;

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

    @Bean(name = "org.openapitools.configuration.EnumConverterConfiguration.errorCodeConverter")
    Converter<String, ErrorCode> errorCodeConverter() {
        return new Converter<String, ErrorCode>() {
            @Override
            public ErrorCode convert(String source) {
                return ErrorCode.fromValue(source);
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

}
