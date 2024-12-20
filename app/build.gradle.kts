plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.ita.condominio"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ita.condominio"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.cronet.embedded)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    implementation("androidx.compose.ui:ui:1.5.3")
    //implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation ("androidx.compose.material3:material3:1.3.0")
    implementation ("androidx.compose.material3:material3-window-size-class:1.3.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.3")
    implementation("androidx.compose.material:material:1.4.0") // Reemplaza con la versión correspondiente
    implementation("androidx.compose.material:material-icons-core:1.4.0") // Reemplaza con la versión correspondiente
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    implementation ("com.google.zxing:core:3.4.1") // Para la funcionalidad básica de QR
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.paypal.sdk:paypal-android-sdk:2.16.0")
    implementation ("androidx.compose.material:material-icons-extended:1.4.0")
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(kotlin("script-runtime"))
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.33.0-alpha")
    implementation("androidx.appcompat:appcompat:1.7.0")

    //Biometricos
    implementation ("androidx.biometric:biometric:1.2.0-alpha03")
    implementation("androidx.asynclayoutinflater:asynclayoutinflater-appcompat:1.1.0-alpha01")



    implementation ("androidx.appcompat:appcompat:1.6.1") // Para la compatibilidad con versiones anteriores
    implementation ("androidx.core:core-ktx:1.10.1")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation ("androidx.room:room-runtime:2.5.0") // Usa la versión más reciente
    val room_version= "2.6.1"
    ksp("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:2.5.0")

    implementation ("androidx.compose.runtime:runtime-livedata:1.5.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
}