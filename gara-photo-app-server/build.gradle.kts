import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val versions by extra {
    mapOf(
        "java" to "21",
        "kotlin" to "1.9.23",
        "springBoot" to "3.2.5",
        "restAssured" to "5.4.0",
        "mybatis" to "3.0.3",
        "jackson-module-kotlin" to "2.17.1",
        "twitter4j-core" to "4.0.7",
        "twitter4j-v2" to "1.4.3",
        "h2" to "2.2.224",
        "mockito-kotlin" to "5.3.1",
        "rider-junit5" to "1.42.0",
        "wiremock" to "3.6.0",
        "kotest" to "5.9.1",
        "kotest-spring" to "1.3.0",
        "detekt" to "1.23.6",
    )
}

plugins {
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.spring") version "1.9.23"
    id("org.sonarqube") version "5.0.0.4638"
    id("jacoco")
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

group = "org.contourgara"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
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
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:${versions["mybatis"]}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${versions["jackson-module-kotlin"]}")
    implementation("org.twitter4j:twitter4j-core:${versions["twitter4j-core"]}")
//    implementation("io.github.takke:jp.takke.twitter4j-v2:${versions["twitter4j-v2"]}")
    implementation(fileTree("lib"))
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5:${versions["kotest"]}")
    testImplementation("io.kotest:kotest-assertions-core:${versions["kotest"]}")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${versions["kotest-spring"]}")
    testImplementation("io.rest-assured:rest-assured:${versions["restAssured"]}")
    testImplementation("io.rest-assured:spring-mock-mvc:${versions["restAssured"]}")
    testImplementation("io.rest-assured:spring-mock-mvc-kotlin-extensions:${versions["restAssured"]}")
    testImplementation("com.h2database:h2:${versions["h2"]}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${versions["mockito-kotlin"]}")
    testImplementation("com.github.database-rider:rider-junit5:${versions["rider-junit5"]}")
    testImplementation("org.wiremock:wiremock-jetty12:${versions["wiremock"]}")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${versions["detekt"]}")
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
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}

plugins.withType<JacocoPlugin> {
    tasks["test"].finalizedBy("jacocoTestReport")
}

detekt {
    parallel = true
    ignoreFailures = false
}
