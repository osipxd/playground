import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    // 1. Apply assignment plugin
    kotlin("plugin.assignment") version "1.8.20"
}

// 2. Define annotation we will use to mark classes supporting assignment overloading
assignment {
    annotation("ValueContainer")
}

// 3. (optional) I want to use K2 compiler :P
tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        languageVersion = KotlinVersion.KOTLIN_2_0
    }
}
