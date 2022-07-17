pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Register the AndroidX snapshot repository first so snapshots don't attempt (and fail)
        // to download from the non-snapshot repositories
        maven(url = "https://androidx.dev/snapshots/builds/8455591/artifacts/repository") {
            content {
                // The AndroidX snapshot repository will only have androidx artifacts, don't
                // bother trying to find other ones
                includeGroupByRegex("androidx\\..*")
            }
        }
        google()
        mavenCentral()
    }
}
rootProject.name = "NowInAndroidClone"
include(":app")
include(":benchmark")
include(":core-model")
include(":core-common")
include(":core-data")
include(":core-testing")
include(":core-database")
include(":core-datastore")
include(":core-datastore-test")
include(":core-network")
include(":core-data")
include(":core-data-test")
include(":sync")
include(":lint")
