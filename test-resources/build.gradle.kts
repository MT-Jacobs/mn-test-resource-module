import io.micronaut.testresources.buildtools.KnownModules.*

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp")
    id("io.micronaut.test-resources")
}

repositories {
    mavenCentral()
}

micronaut {
    testResources {
        additionalModules.addAll(
            REDIS,
            CONTROL_PANEL,
        )
    }
}