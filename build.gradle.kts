import org.gradle.api.tasks.testing.logging.TestExceptionFormat

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
   `java-library`
   `maven-publish`
   signing
   alias(libs.plugins.kotlin.jvm)
}

group = "io.kotest.extensions"
version = Ci.version

dependencies {
   implementation(libs.kotlin.stdlib)
   implementation(libs.kotest.framework.api)
   implementation(libs.h2)
   testImplementation(libs.kotest.runner.junit5)
   testImplementation(libs.kotest.assertions.core)
}

tasks.named<Test>("test") {
   useJUnitPlatform()
   testLogging {
      showExceptions = true
      showStandardStreams = true
      exceptionFormat = TestExceptionFormat.FULL
   }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
   kotlinOptions.jvmTarget = "1.8"
   kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
}

repositories {
   mavenLocal()
   mavenCentral()
   maven {
      url = uri("https://oss.sonatype.org/content/repositories/snapshots")
   }
}

apply("./publish.gradle.kts")
