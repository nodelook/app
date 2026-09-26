pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Enforce centralized repositories
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "NodeLook"

include(":android", ":common", ":websiteBuilder", ":cli", ":composeApp", ":desktopApp", ":viewmodels", ":screenshotTests")
