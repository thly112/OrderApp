plugins {
    alias(libs.plugins.android.application)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
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
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation (libs.rxjava3.retrofit.adapter)
}