import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    alias(libs.plugins.android.library)
    id("maven-publish")
}

android {
    namespace = "com.itwingtech.myawesomeads"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_20
        targetCompatibility = JavaVersion.VERSION_20
    }

    buildFeatures {
        viewBinding = true
    }

    publishing {
        singleVariant("release")
    }

}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(project.components["release"]) // Use this line to access components
                groupId = "com.github.shahzamansurani"
                artifactId = "myawesomeads"
                version = android.defaultConfig.versionName // or manually "1.0"
            }
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.gms:play-services-ads:23.3.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.intuit.sdp:sdp-android:1.1.1")
    implementation("com.intuit.ssp:ssp-android:1.1.1")
    implementation("com.airbnb.android:lottie:6.4.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("com.onesignal:OneSignal:5.1.17")
    implementation("androidx.lifecycle:lifecycle-process:2.8.5")
}