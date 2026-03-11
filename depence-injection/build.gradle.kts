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
tasks.test{
    useJUnitPlatform()
}

dependencies {
    implementation(project(":usecase"))
    testImplementation(project(":data"))
    testImplementation(project(":domain"))
    implementation(project(":data-supabase"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.test)

    testImplementation(platform(libs.junit5.bom))
    testImplementation(libs.junit5.jupiter.api)
    testRuntimeOnly(libs.junit5.jupiter.engine)

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")

}
