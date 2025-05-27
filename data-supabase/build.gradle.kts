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

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":data"))
    //implementation(project(":domain-core"))
    implementation(project(":domain"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation("io.insert-koin:koin-test:4.0.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")


    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.ktor:ktor-client-android:3.0.3")
}
