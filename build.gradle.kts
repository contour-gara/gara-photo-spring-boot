plugins {
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("com.avast.gradle.docker-compose") version "0.17.6"
}
