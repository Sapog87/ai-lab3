plugins {
    java
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "ru.m1.ai"
version = "0.0.1-SNAPSHOT"
description = "lab3"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven {
        name = "Central Portal Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        mavenContent {
            snapshotsOnly()
        }
    }
}

dependencies {
    implementation(platform("dev.langchain4j:langchain4j-bom:1.9.1"))

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("dev.langchain4j:langchain4j-spring-boot-starter")
    implementation("dev.langchain4j:langchain4j-ollama-spring-boot-starter")
    implementation("dev.langchain4j:langchain4j-reactor")
    implementation("dev.langchain4j:langchain4j-pgvector")
    implementation("dev.langchain4j:langchain4j-easy-rag")

    implementation("org.postgresql:postgresql")

    implementation("com.opencsv:opencsv:5.9")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
