# MovieCatch

Compose playground for learning purpose

* Uses [Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines/coroutines-guide.html)
* Uses [Retrofit](https://square.github.io/retrofit/)
* Uses Room, Navigation, ViewModel
* Uses [Hilt](https://dagger.dev/hilt/) for dependency injection  

![2](https://github.com/umutsaydam/MovieCatch/assets/69711134/eda4f73f-bf43-4f3d-90c6-e14a7fa032be)
![1](https://github.com/umutsaydam/MovieCatch/assets/69711134/13911301-2ecf-4566-96e7-66399c75ea50)
![4](https://github.com/umutsaydam/MovieCatch/assets/69711134/620707e3-4329-4d0e-a7c1-d1305edc48b4)
![3](https://github.com/umutsaydam/MovieCatch/assets/69711134/33b08057-17f3-47ba-88b8-d7a4b0fcfdd0)

## Prerequisites

* Android Studio Flamingo | 2022.2.1
* Min SDK 24
* Target SDK 33
* AGP 8.0.0
* Java 17
* Kotlin 1.8.0


## Setup

1. Clone this repository, `git clone https://github.com/wisnukurniawan/Compose-ToDo.git`
2. Open via [Android studio](https://developer.android.com/studio)
3. Sync the project, **File -> Sync Project with Gradle files**

## Build.gradle(Project)
  ```
plugins {
    id 'com.android.application' version '8.0.2' apply false
    id 'com.android.library' version '8.0.2' apply false
    id 'org.jetbrains.kotlin.android' version '1.8.0' apply false
    id 'com.google.dagger.hilt.android' version '2.44' apply false
}
  ```


## Settings.gradle(Project settings)
  ```
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
rootProject.name = "MovieCatch"
include ':app'

  ```

## Build.gradle(:app)
  ```
    plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-kapt'
    id 'com.google.dagger.hilt.android'
}

android {
    namespace 'com.musicplayer.moviecatch'
    compileSdk 34

    defaultConfig {
        applicationId "com.musicplayer.moviecatch"
        minSdk 24
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_17
        targetCompatibility JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = '17'
    }

    buildFeatures{
        viewBinding true
    }
}

dependencies {

    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.7.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.7.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    implementation 'com.google.dagger:hilt-android:2.44'
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.github.bumptech.glide:glide:4.12.0'
    kapt 'com.google.dagger:hilt-android-compiler:2.44'
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    implementation 'com.github.ismaeldivita:chip-navigation-bar:1.3.4'

    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2'

    implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.1.0'

    implementation 'androidx.room:room-runtime:2.5.2'
    kapt 'androidx.room:room-compiler:2.5.2'
    implementation 'androidx.room:room-ktx:2.5.2'

    implementation 'androidx.paging:paging-common-ktx:3.2.0'
    implementation 'androidx.paging:paging-runtime-ktx:3.2.0'

    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
}
  ```
