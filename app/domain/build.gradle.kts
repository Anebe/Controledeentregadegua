plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)

    kotlin("plugin.serialization") version "1.9.0"
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")




}