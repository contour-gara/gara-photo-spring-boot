plugins {
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.spring") version "2.1.21"
    id("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("com.avast.gradle.docker-compose") version "0.17.12"
}
