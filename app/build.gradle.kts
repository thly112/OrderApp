plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.oderapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.oderapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.constraintlayout.core)

    implementation(fileTree(mapOf(
        "dir" to "D:\\ThLy\\Junior\\LapTrinhDiDong",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to emptyList<String>()
    )) as FileTree)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // glider
    implementation (libs.glide)
    //RxJava
    implementation (libs.rxandroid)
    implementation (libs.rxjava)
    // Retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.rxjava3.retrofit.adapter)
    // Navigation Component
    implementation (libs.navigation.fragment.v277)
    implementation (libs.navigation.ui.v277)
    //brade
    implementation (libs.notification.badge)
    // Material
    implementation (libs.material.v1110)
    implementation (libs.imageslideshow)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    // even bus
    implementation(libs.eventbus)
    //paper
    implementation (libs.paperdb)
    implementation (libs.gson)
    //zalopay
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("commons-codec:commons-codec:1.14")
    //momo
//    implementation ("com.github.momo-wallet:mobile-sdk:1.0.7")
    //firebase
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Also add the dependencies for the Credential Manager libraries and specify their versions
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation("com.google.firebase:firebase-messaging:24.1.1")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

}