plugins {
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.10"
    id("org.sonarqube") version "6.0.1.5171"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("com.avast.gradle.docker-compose") version "0.17.12"
}
