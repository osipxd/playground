# Kotlin Assignment Plugin

This project demonstrates usage of the assignment plugin. This plugin allows to overload assignment operator.

## Usage

> **Note**  
> Make sure you use latest version of Kotlin and IDEA.
> It is recommended to use at least **Kotlin 1.8.20** and **IDEA 2022.3** _(Android Studio Giraffe)_.

Apply plugin to the project using `plugins` block in `build.gradle.kts`:

```kotlin
plugins {
    kotlin("plugin.assignment") version "1.8.20"
}
```

Then specify the annotation that will enable assignment overloading for class

```kotlin
assignment {
    annotation("com.my.ValueContainer")
}
```

If the class annotated with `com.my.ValueContainer`, you can overload assignment operator for the class:

```kotlin
annotation class ValueContainer

@ValueContainer
class Box<T>(var value: T) {

    fun assign(value: T) {
        this.value = value
    }

    fun assign(box: Box<T>) {
        this.value = box.value
    }
}
```

Then you can declare fields with type `Box` and use assignment operator

```kotlin
class Config(val name: Box<String>)

fun main() {
    val config = Config(Box("default_name"))
    config.name = "custom_name"
    config.name = Box("another_name")
}
```

> See more complex example in [FreezableBox.kt](src/main/kotlin/FreezableBox.kt)

## Requirements and limitations

Assignment function [requirements](https://github.com/JetBrains/kotlin/blob/v1.8.20/plugins/assign-plugin/assign-plugin.k2/src/org/jetbrains/kotlin/assignment/plugin/k2/diagnostics/FirAssignmentPluginFunctionChecker.kt):

- name - `assign`
- exactly one parameter
- return type - `Unit`

Usage [limitations](https://github.com/JetBrains/kotlin/blob/v1.8.20/plugins/assign-plugin/assign-plugin.k2/src/org/jetbrains/kotlin/assignment/plugin/k2/FirAssignmentPluginAssignAltererExtension.kt#L36-L43)

- overloaded assignment can be used only with `val` to not conflict with the true assignment operator
- can't be used with local variables, so the following code will not compile:
  ```kotlin
  fun main() {
      val boxedValue = Box(0)
      boxedValue = 42 // [VAL_REASSIGNMENT] Val cannot be reassigned
  }
  ```

## Useful links

- [assignment-plugin sources](https://github.com/JetBrains/kotlin/tree/v1.8.20/plugins/assign-plugin)
- [assignment-plugin test data](https://github.com/JetBrains/kotlin/tree/v1.8.20/plugins/assign-plugin/testData)
- [Gradle plugin](https://github.com/JetBrains/kotlin/tree/v1.8.20/libraries/tools/kotlin-assignment)
- [Known issues](https://youtrack.jetbrains.com/issues?q=%22Kotlin%20assignment%20plugin%22%20%23Unresolved)
- [Gradle: Kotlin DSL property assignment](https://docs.gradle.org/8.1/userguide/kotlin_dsl.html#kotdsl:assignment)
