plugins {
    java
    idea
    id("org.springframework.boot") version "4.0.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.16.0"
}

group = "com.tripify"
version = "1.0-SNAPSHOT"
description = "Auth microservice"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.postgresql:postgresql:42.7.7")

    implementation("redis.clients:jedis:6.0.0")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.14")

    implementation("org.springframework.boot:spring-boot-starter-liquibase")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

val openApiGeneratedDir = layout.buildDirectory.dir("generated/openapi")

openApiGenerate {
    generatorName.set("spring")
    library.set("spring-boot")

    inputSpec.set("$rootDir/src/main/resources/openapi/openapi.yaml")
    outputDir.set(openApiGeneratedDir.get().asFile.path)

    apiPackage.set("com.tripify.auth.generated.api")
    modelPackage.set("com.tripify.auth.generated.model")
    invokerPackage.set("com.tripify.auth.generated.invoker")

    generateApiTests.set(false)
    generateModelTests.set(false)
    generateApiDocumentation.set(false)
    generateModelDocumentation.set(false)

    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true",
            "useJakartaEe" to "true",
            "useTags" to "true",
            "dateLibrary" to "java8",
            "openApiNullable" to "false",
            "skipDefaultInterface" to "true",
            "useBeanValidation" to "true",
            "performBeanValidation" to "true"
        )
    )
}

sourceSets {
    main {
        java {
            srcDir(openApiGeneratedDir.map { it.dir("src/main/java") })
        }
    }
}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("openApiHtml") {
    generatorName.set("html2")

    inputSpec.set("$rootDir/src/main/resources/openapi/openapi.yaml")
    outputDir.set(layout.buildDirectory.dir("generated/openapi-html").get().asFile.path)
}

tasks.compileJava {
    dependsOn(tasks.openApiGenerate)
    dependsOn(tasks.named("openApiHtml"))
}

tasks.test {
    useJUnitPlatform()
}
