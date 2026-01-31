// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

// Force versions for the build script classpath
buildscript {
    configurations.all {
        resolutionStrategy {
            force("com.squareup:javapoet:1.13.0")
        }
    }
}

// Resolve dependency conflicts
allprojects {
    configurations.all {
        resolutionStrategy {
            // Force correct versions to avoid conflicts with Hilt
            force(libs.javapoet)
        }
    }
}

