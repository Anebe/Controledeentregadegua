plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
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
    implementation(project(":domain"))
//    implementation(project(":usecase"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation("io.insert-koin:koin-test:4.0.2")
}