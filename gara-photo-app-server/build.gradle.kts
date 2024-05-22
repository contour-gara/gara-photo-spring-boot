import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val versions by extra {
    mapOf(
        "java" to "17",
        "kotlin" to "1.9.23",
        "springBoot" to "3.2.4",
        "restAssured" to "5.4.0",
        "mybatis" to "3.0.3",
        "jackson-module-kotlin" to "2.17.0",
        "twitter4j-core" to "4.0.7",
        "twitter4j-v2" to "1.4.3",
        "h2" to "2.2.224",
        "mockito-kotlin" to "5.3.1",
        "rider-junit5" to "1.42.0",
        "wiremock" to "3.5.4",
    )
}

plugins {
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
}

group = "org.contourgara"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${versions["springBoot"]}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:${versions["mybatis"]}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${versions["jackson-module-kotlin"]}")
    implementation("org.twitter4j:twitter4j-core:${versions["twitter4j-core"]}")
//    implementation("io.github.takke:jp.takke.twitter4j-v2:${versions["twitter4j-v2"]}")
    implementation(fileTree("lib"))
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.rest-assured:rest-assured:${versions["restAssured"]}")
    testImplementation("io.rest-assured:spring-mock-mvc:${versions["restAssured"]}")
    testImplementation("com.h2database:h2:${versions["h2"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${versions["mockito-kotlin"]}")
    testImplementation("com.github.database-rider:rider-junit5:${versions["rider-junit5"]}")
    testImplementation("org.wiremock:wiremock-jetty12:${versions["wiremock"]}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "${versions["java"]}"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.PASSED,
            TestLogEvent.SKIPPED,
            TestLogEvent.STANDARD_OUT
        )

        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
        showExceptions = true
        showCauses = true
        showStackTraces = true
    }
}

sonar {
    properties {
        property("sonar.exclusions", "src/main/kotlin/org/contourgara/garaphotospringboot/GaraPhotoSpringBootApplication.kt")
    }
}

jacoco {
    toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(false)
    }
}

plugins.withType<JacocoPlugin> {
    tasks["test"].finalizedBy("jacocoTestReport")
}
