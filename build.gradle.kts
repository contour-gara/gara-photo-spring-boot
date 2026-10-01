plugins {
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("org.sonarqube") version "7.5.0.8588"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("com.avast.gradle.docker-compose") version "0.17.19"
}
