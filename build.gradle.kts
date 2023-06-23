plugins {
    kotlin("jvm") version "1.8.21"
    `maven-publish`
}

group = "com.github.ShatteredSoftware"
val version: String by project

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.github.ShatteredSoftware"
            artifactId = "datastore"
            version = version

            from(components["java"])
        }
    }
}