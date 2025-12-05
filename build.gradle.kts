plugins {
    kotlin("jvm") version "1.9.20"
    application
}

group = "com.algocompiler"
version = "1.6.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.algocompiler.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.algocompiler.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
