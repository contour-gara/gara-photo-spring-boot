plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"
    id("org.sonarqube") version "5.1.0.4882"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("com.avast.gradle.docker-compose") version "0.17.10"
}
