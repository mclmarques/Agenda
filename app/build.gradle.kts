plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.android.puc.agenda"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.puc.agenda"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    //Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    //Testing
    // JUnit for unit testing
    testImplementation(libs.junit)
    // AndroidX Test - Core Testing library
    androidTestImplementation(libs.junit.v115)
    androidTestImplementation(libs.core)

    // Room testing dependencies (allows in-memory databases for fast testing)
    testImplementation(libs.room.testing)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}