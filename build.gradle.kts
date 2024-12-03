plugins {
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.spring") version "2.0.21"
    id("org.sonarqube") version "6.0.1.5171"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("com.avast.gradle.docker-compose") version "0.17.11"
}
