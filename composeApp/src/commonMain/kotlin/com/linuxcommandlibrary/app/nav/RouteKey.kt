package com.nodelook.app.nav

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.nodelook.app.Route

internal enum class RouteKey {
    Basics,
    Commands,
    Tips,
}

internal val RouteKey.route: Route
    get() = when (this) {
        RouteKey.Basics -> Route.Basics
        RouteKey.Commands -> Route.Commands
        RouteKey.Tips -> Route.Tips
    }

internal fun NavDestination?.toRouteKey(): RouteKey? = when {
    this == null -> null
    hasRoute<Route.Basics>() -> RouteKey.Basics
    hasRoute<Route.Commands>() -> RouteKey.Commands
    hasRoute<Route.Tips>() -> RouteKey.Tips
    else -> null
}

internal fun Route.toRouteKey(): RouteKey = when (this) {
    is Route.Basics -> RouteKey.Basics
    is Route.Commands -> RouteKey.Commands
    is Route.Tips -> RouteKey.Tips
}
