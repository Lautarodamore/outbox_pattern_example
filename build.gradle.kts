import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

buildscript {
    dependencies {
        classpath("org.postgresql:postgresql:42.2.18")
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-simple:1.7.36")
    implementation("com.rabbitmq:amqp-client:5.15.0")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("io.javalin:javalin:4.6.4")
    implementation("com.nbottarini:asimov-json:0.5.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}