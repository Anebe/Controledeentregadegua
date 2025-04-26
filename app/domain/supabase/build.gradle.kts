
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

tasks.test {
    useJUnitPlatform()
}
tasks.register("chave") {


}
dependencies {
    implementation(project(":app:domain"))

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation("io.insert-koin:koin-test:4.0.2")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))  // BOM para gerenciamento de versões
    testImplementation("org.junit.jupiter:junit-jupiter-api")  // API para escrever testes
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")


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
        "-DSUPABASE_KEY=$supabaseKey",
        "-DSUPABASE_URL=$supabaseUrl"
    ))
}
tasks.withType<Test> {
    systemProperty("SUPABASE_KEY", supabaseKey)
    systemProperty("SUPABASE_URL", supabaseUrl)
}



