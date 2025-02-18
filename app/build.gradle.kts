import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization") version "1.9.0"
    id("com.google.devtools.ksp")
    kotlin("kapt")

}


android {
    namespace = "com.gabriel_barros.controle_entregua_agua"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gabriel_barros.controle_entregua_agua"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val spreadsheetKey: String? = localProperties.getProperty("SPREADSHEET_KEY")
        buildConfigField("String", "SPREADSHEET_KEY", "\"$spreadsheetKey\"")
        val supabaseKey: String? = localProperties.getProperty("SUPABASE_KEY")
        buildConfigField( "String", "SUPABASE_KEY", "\"$supabaseKey\"")

    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    ksp(libs.ksp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")


    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.room.ktx)
    implementation(libs.navigation.compose)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation (libs.retrofit)
    implementation (libs.retrofit.gson)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation (libs.sheets.compose.dialogs.core)
    implementation (libs.sheets.compose.dialogs.calendar)

    implementation(platform("io.github.jan-tennert.supabase:bom:3.0.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")
    implementation("io.github.jan-tennert.supabase:auth-kt")
    implementation("io.github.jan-tennert.supabase:realtime-kt")
    implementation("io.ktor:ktor-client-android:3.0.3")

    implementation("io.insert-koin:koin-android:4.0.2")
    implementation ("io.insert-koin:koin-androidx-compose:4.0.2")

}