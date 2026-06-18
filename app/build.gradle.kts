plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.0.0"
    id 'kotlin-kapt'
}

android {
    namespace = "com.gonzalez.tupmundial2026"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.gonzalez.tupmundial2026"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    // Retrofit core
    implementation(libs.retrofit)
    // 1. Librería base de Kotlinx Serialization (JSON)
    implementation(libs.kotlinx.serialization.json)
    // 2. Convertidor oficial de Retrofit para Kotlinx Serialization (Square)
    // Esta es la implementación mantenida por los creadores de Retrofit
    implementation(libs.converter.kotlinx.serialization)
    // Implementación principal (incluye dependencias de Android)
    implementation(libs.androidx.datastore.preferences)
    // Versión estable recomendada (mayo 2026)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons)
    // Room
    implementation "androidx.room:room-runtime:2.6.1"
    implementation "androidx.room:room-ktx:2.6.1"
    kapt "androidx.room:room-compiler:2.6.1"

}
