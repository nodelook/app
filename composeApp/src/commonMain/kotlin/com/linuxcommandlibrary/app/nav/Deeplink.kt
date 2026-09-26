package com.nodelook.app.nav

import com.nodelook.app.Route

internal sealed class InitialSelection {
    data class Command(val name: String) : InitialSelection()
    data class Basics(val id: String, val title: String) : InitialSelection()
    data class SearchQuery(val query: String) : InitialSelection()
}

internal data class DeeplinkResult(val route: Route, val selection: InitialSelection?)

internal fun parseDeeplink(url: String?): DeeplinkResult? {
    if (url == null) return null

    return when {
        url.endsWith("/basics.html") || url.endsWith("/basics") ->
            DeeplinkResult(Route.Basics, null)

        url.endsWith("/tips.html") || url.endsWith("/tips") ->
            DeeplinkResult(Route.Tips, null)

        url.contains("/man/") -> {
            val commandName = url.substringAfterLast("/man/").removeSuffix(".html")
            DeeplinkResult(Route.Commands, InitialSelection.Command(commandName))
        }

        url.contains("/basic/") -> {
            val categoryId = url.substringAfterLast("/basic/").removeSuffix(".html")
            DeeplinkResult(
                Route.Basics,
                InitialSelection.Basics(id = categoryId, title = categoryId),
            )
        }

        url.contains("/search/") -> {
            val query = url.substringAfterLast("/search/").removeSuffix(".html")
            DeeplinkResult(Route.Commands, InitialSelection.SearchQuery(query))
        }

        url.endsWith("/") || url.endsWith("/index.html") ->
            DeeplinkResult(Route.Commands, null)

        else -> null
    }
}
