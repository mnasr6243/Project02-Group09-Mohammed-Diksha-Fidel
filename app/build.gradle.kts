plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.project02_group09_mohammed_diksha_fidel"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.project02_group09_mohammed_diksha_fidel"
        minSdk = 33
        targetSdk = 36
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

    // Standard AndroidX Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Room Database Libraries
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // Local Unit Test Dependencies
    testImplementation(libs.junit)

    // Android Instrumentation Test Dependencies
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation("androidx.test:core:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    // Database Testing (Room Testing)
    androidTestImplementation("androidx.room:room-testing:$room_version")

    // LiveData/Architecture Component Testing
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")

    // CRITICAL FIX: Intent Testing Dependency (resolves 'package androidx.test.espresso.intent does not exist')
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")
}