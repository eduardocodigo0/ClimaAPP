plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

group = "me.eduardo"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.0")
    //Easy permissions
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")
    //Google Play Services
    implementation("com.google.android.gms:play-services-location:18.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    //Picasso
    implementation("com.squareup.picasso:picasso:2.71828")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "me.eduardo.androidApp"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}