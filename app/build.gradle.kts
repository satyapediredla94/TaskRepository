plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = ConfigData.applicationId
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.applicationId
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = ConfigData.testRunner
        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    kapt {
        correctErrorTypes = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("release") {
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
        jvmTarget = ConfigData.jvm
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.version
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Deps.appCompat)
    implementation(Deps.materialDesign)
    implementation(Deps.constraintLayout)
    implementation(AndroidLifeCycle.lifecycle)

    //Compose
    implementation(ComposeDeps.composeMaterial)
    implementation(ComposeDeps.composeUi)
    implementation(ComposeDeps.composeActivity)
    implementation(ComposeDeps.composeToolPreview)
    implementation(ComposeDeps.composeRunTime)
    implementation(ComposeDeps.composeCompiler)


    testImplementation(Deps.junit)
    androidTestImplementation(TestingDeps.jUnit)
    androidTestImplementation(TestingDeps.espresso)
    androidTestImplementation(TestingDeps.junit4)
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.2")

    //Room Deps
    implementation(RoomDeps.roomRunTime)
    implementation(RoomDeps.roomKotlin)
    kapt(RoomDeps.roomCompiler)

    //Dagger Hilt
    implementation(DaggerHilt.daggerHilt)
    kapt(DaggerHilt.hiltCompiler)

    //Navigation
    implementation(NavigationDeps.navigation)

    //Gson
    implementation(GsonParser.gson)

    //Timber
    implementation(Deps.timber)

}