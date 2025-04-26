import java.util.Properties

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
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation (libs.retrofit)
    implementation (libs.retrofit.gson)
}


val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}
val spreadsheetKey = localProperties.getProperty("SPREADSHEET_KEY") ?: ""

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
        "-DSPREADSHEET_KEY=$spreadsheetKey",
    ))
}

