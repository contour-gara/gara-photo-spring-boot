plugins {
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.spring") version "2.2.20"
    id("org.sonarqube") version "6.3.1.5724"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("com.avast.gradle.docker-compose") version "0.17.15"
}
