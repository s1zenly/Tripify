plugins {
	java
	idea
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.tripify"
version = "0.0.1-SNAPSHOT"
description = "Hotels Parser microservice"

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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.google.guava:guava:33.2.1-jre")

	implementation("org.springframework.boot:spring-boot-starter-kafka")
	implementation("org.apache.kafka:kafka-clients:3.9.1") {
		because("Fixes CVEs: CVE-2025-27817, CVE-2025-27818, CVE-2024-31141")
	}

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.3")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}