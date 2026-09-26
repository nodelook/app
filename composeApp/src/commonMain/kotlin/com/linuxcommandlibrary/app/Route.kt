package com.nodelook.app

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Basics : Route()

    @Serializable
    data object Commands : Route()

    @Serializable
    data object Tips : Route()
}
