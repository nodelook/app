package com.nodelook.app.data

import com.nodelook.shared.TipSectionElement
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class BasicCategory(
    val id: String,
    val title: String,
)

data class BasicGroup(
    val id: Long,
    val description: String,
)

data class BasicGroupMatch(
    val groupId: Long,
    val description: String,
    val categoryId: String,
    val categoryTitle: String,
)

data class CommandInfo(
    val id: Long,
    val name: String,
)

data class CommandSectionInfo(
    val id: Long,
    val title: String,
    val content: String,
    val parsedContent: ImmutableList<TipSectionElement> = persistentListOf(),
)

fun CommandSectionInfo.getSortPriority(): Int = com.nodelook.shared.getSectionSortPriority(title)
