/**
 * To define plugins
 */
object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Gradle.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}" }
}

/**
 * To define dependencies
 */
object Deps {
    val appCompat by lazy { "androidx.appcompat:appcompat:${AndroidCore.appCompat}" }
    val timber by lazy { "com.jakewharton.timber:timber:${Timber.timber}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Kotlin.version}" }
    val materialDesign by lazy { "com.google.android.material:material:${AndroidCore.materialVersion}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${AndroidCore.constraintLayout}" }
    val junit by lazy { "junit:junit:${AndroidCore.jUnit}" }
}

object GsonParser {
    val gson by lazy { "com.squareup.retrofit2:converter-gson:${Gson.version}" }
}

object AndroidLifeCycle {
    val lifecycle by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${LifeCycle.runTime}" }
}

object ComposeDeps {
    val composeActivity by lazy { "androidx.activity:activity-compose:${Compose.activityCompose}" }
    val composeUi by lazy { "androidx.compose.ui:ui:${Compose.version}" }
    val composeToolPreview by lazy { "androidx.compose.ui:ui-tooling-preview:${Compose.version}" }
    val composeMaterial by lazy { "androidx.compose.material:material:${Compose.materialVersion}" }
    val composeRunTime by lazy { "androidx.compose.runtime:runtime:${Compose.version}" }
    val composeCompiler by lazy { "androidx.compose.compiler:compiler:${Compose.version}" }
}

object NavigationDeps {
    val navigation by lazy { "androidx.navigation:navigation-compose:${Navigation.version}" }
}

object TestingDeps {
    val jUnit by lazy { "androidx.test.ext:junit:${Testing.jUnit}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Testing.espresso}" }
    val junit4 by lazy { "androidx.compose.ui:ui-test-junit4:${Testing.junit4}" }
}

object RoomDeps {
    val roomRunTime by lazy { "androidx.room:room-runtime:${Room.version}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Room.version}" }
    val roomKotlin by lazy { "androidx.room:room-ktx:${Room.version}" }
}

object DaggerHilt {
    val daggerHilt by lazy { "com.google.dagger:hilt-android:${Hilt.version}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Hilt.version}" }
}