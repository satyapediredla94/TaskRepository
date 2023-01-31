plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("jacoco")
}

jacoco {
    version = "0.8.8"
}

android {
    namespace = ConfigData.applicationId
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion
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
            enableUnitTestCoverage = true
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
            excludes += "/META-INF/proguard/androidx-annotations.pro"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {

    implementation(Deps.appCompat)
    implementation(Deps.androidCore)
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
    implementation(ComposeDeps.composeLifecycle)


    testImplementation(Deps.junit)
    testImplementation(TestingDeps.coroutineTest)
    testImplementation(TestingDeps.archTest)
    androidTestImplementation(TestingDeps.jUnit)
    androidTestImplementation(TestingDeps.espresso)
    androidTestImplementation(TestingDeps.junit4)
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.3")

    //Room Deps
    implementation(RoomDeps.roomRunTime)
    implementation(RoomDeps.roomKotlin)
    kapt(RoomDeps.roomCompiler)

    //Dagger Hilt
    implementation(DaggerHilt.daggerHiltLegacy)
    implementation(DaggerHilt.hiltCompilerLegacy)
    implementation(DaggerHilt.daggerHilt)
    implementation(DaggerHilt.hiltCompose)
    kapt(DaggerHilt.hiltCompiler)

    //Icons
    implementation(Icons.icons)

    //Gson
    implementation(GsonDeps.gsonVersion)

    //Navigation
    implementation(NavigationDeps.navigation)

    //Timber
    implementation(Deps.timber)

    //Accompanist
    implementation(AccompanistDependencies.statusBar)

}