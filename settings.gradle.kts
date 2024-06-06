plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name="mn-test-resource-modules"
include("application", "test-resources")
