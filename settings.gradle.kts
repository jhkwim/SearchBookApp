pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SearchBookApp"
include(":app")
include(":core:network")
include(":core:data")
include(":core:model")
include(":feature:search")
include(":feature:detail")
include(":core:database")
