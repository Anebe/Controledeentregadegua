pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Controle de entrega de água"
include(":app")
include(":app:domain")
include(":app:domain:supabase")
include(":app:domain:room")
include(":app:ioc")
include(":app:domain:usecase")
include(":app:domain:spreedsheetgoogle")
