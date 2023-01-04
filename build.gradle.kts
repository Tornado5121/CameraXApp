// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
    extra.apply {
        set("lifecycle_version", "2.4.1")
        set("koin_version", "3.2.0-beta-1")
        set("camerax_version", "1.1.0-beta03")
    }
}