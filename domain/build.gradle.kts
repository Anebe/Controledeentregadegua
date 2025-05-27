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

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation("io.insert-koin:koin-test:4.0.2")

    testImplementation(libs.junit)
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")


}