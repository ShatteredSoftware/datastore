<p align="center">
    <h1>datastore</h1>
    <a href="https://github.com/ShatteredSoftware/datastore/blob/master/LICENSE"><img alt="License" src="https://img.shields.io/github/license/ShatteredSoftware/datastore?style=for-the-badge&logo=github"/></a>
    <a href="https://github.com/ShatteredSoftware/datastore/issues"><img alt="GitHub Issues" src="https://img.shields.io/github/issues/ShatteredSoftware/datastore?style=for-the-badge&logo=github" /></a>
    <a href="https://discord.gg/zUbNX9t"><img alt="Discord" src="https://img.shields.io/badge/Get%20Help-On%20Discord-%237289DA?style=for-the-badge&logo=discord" /></a>
    <a href="ko-fi.com/uberpilot"><img alt="Ko-Fi" src="https://img.shields.io/badge/Support-on%20Ko--fi-%23F16061?style=for-the-badge&logo=ko-fi" /></a>
</p>
A safer data structure for generic data in Kotlin.

## Usage:
```kotlin
val dataStore = GenericDataStore()
// Simple interface
dataStore.set("test", "Hello, world!")
dataStore.get("test", String::class.java) // "Hello, world!"

// And a shorthand with Kotlin operators
dataStore["test"] = "Hello, world!"
dataStore["number"] = 5
val value: String? = dataStore["test"] // "Hello, world!"
val stringNumber: String? = dataStore["number"] // null
val numberNumber: Int? = dataStore["number"] // 5

// Allows defaults
val valueWithDefault = dataStore.getOrDef("none", "default") // "default"
// And a shorthand syntax
val valueWithDefault = dataStore["none", "default"] // "default"

// And explicit classes, using javaObjectType for primitives and javaClass for classes
val intValue = dataStore.get("number", Int::class.javaObjectType) // 5
val strValue = dataStore.get("test", String::class.javaClass) // "Hello, world!"
```
