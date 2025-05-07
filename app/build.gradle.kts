import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "1.9.22"
    id("kotlin-kapt") apply true
}

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}
val apiKey: String = localProperties.getProperty("API_KEY") ?: ""
android {
    namespace = "com.deepakjetpackcompose.ainotes"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.deepakjetpackcompose.ainotes"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"$apiKey\"")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

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
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //splash
    implementation("androidx.core:core-splashscreen:1.0.0")
    //navigation
    val nav_version = "2.8.9"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    //viewmodel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    //
    //ktor client
    implementation("io.ktor:ktor-client-core:2.3.6")

    // Ktor Client for Android
    implementation("io.ktor:ktor-client-android:2.3.6")

    // JSON Serialization (for handling JSON responses)
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.6")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    // Content Negotiation (for automatic serialization)
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")

    // Logging (for debugging API requests and responses)
    implementation("io.ktor:ktor-client-logging:2.3.6")

    // SLF4J Logger (for better logging support)
    implementation("org.slf4j:slf4j-simple:2.0.9")

    //lottie animation
    implementation ("com.airbnb.android:lottie-compose:6.4.0")


    //database
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:2.6.1")

    //Extension and support for coroutines
    implementation("androidx.room:room-ktx:$room_version")
    //coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
}