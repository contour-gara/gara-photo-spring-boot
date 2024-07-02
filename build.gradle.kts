plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("com.avast.gradle.docker-compose") version "0.17.6"
}
