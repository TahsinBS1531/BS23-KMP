import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.google.playServices)
//    id("com.google.gms.google-services")
//    alias(libs.plugins.kspCompose)
//    alias(libs.plugins.room)

}


kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
//    sourceSets.commonMain {
//        kotlin.srcDir("build/generated/ksp/metadata")
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
            //Google Maps
            implementation("com.google.maps.android:maps-compose:2.9.0")
            implementation("com.google.android.gms:play-services-location:21.0.1")
            implementation("com.google.android.gms:play-services-maps:18.0.2")
            implementation(libs.firebase.bom)
            implementation(libs.google.accompanist.permissions)


        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("app.cash.paging:paging-runtime-uikit:3.3.0-alpha02-0.5.1")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kamel)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)

            //Paging Library
            implementation("app.cash.paging:paging-common:3.3.0-alpha02-0.5.1")
            implementation("app.cash.paging:paging-compose-common:3.3.0-alpha02-0.5.1")

            //Logging - Kermit
            implementation("co.touchlab:kermit:2.0.4")
            //Room
//            implementation(libs.room.runtime)
//            implementation("androidx.sqlite:sqlite-bundled:2.5.0-SNAPSHOT") //for sqlite drivers related

            //dateTime
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
            //Serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.2")

            //Firebase Authentication
            implementation("dev.gitlive:firebase-auth:2.1.0")
            implementation("dev.gitlive:firebase-firestore:2.1.0")


            //Permission Handling
            implementation("dev.icerock.moko:permissions:0.18.0")



        }
    }
}

buildscript {
    repositories {
        mavenCentral()
    }
}

android {
    namespace = "com.jetbrains.kmpapp"
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.jetbrains.kmpapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
//room {
//    schemaDirectory("$projectDir/schemas")
//}

//dependencies {
//    add("kspCommonMainMetadata", libs.room.compiler)
//}

//tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().configureEach {
//    if (name != "kspCommonMainKotlinMetadata" ) {
//        dependsOn("kspCommonMainKotlinMetadata")
//    }
//}

dependencies {
    implementation(libs.androidx.ui.android)
    implementation(libs.androidx.annotation.jvm)
    implementation(libs.play.services.location)
    implementation(libs.androidx.lifecycle.livedata.core.ktx)
    debugImplementation(libs.androidx.compose.ui.tooling)

    //For moko maps
}
