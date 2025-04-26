import java.util.Properties

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
tasks.test {
    useJUnitPlatform()
}
dependencies {
    implementation(project(":app:domain"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)

    testImplementation(libs.junit.junit)
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:4.13.2")


    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.ktor:ktor-client-android:3.0.3")
}




val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}
val supabaseKey = localProperties.getProperty("SUPABASE_KEY") ?: ""
val supabaseUrl = localProperties.getProperty("SUPABASE_URL") ?: ""

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(listOf(
        "-SUPABASE_KEY=$supabaseKey",
        "-SUPABASE_URL=$supabaseUrl"
    ))
}

