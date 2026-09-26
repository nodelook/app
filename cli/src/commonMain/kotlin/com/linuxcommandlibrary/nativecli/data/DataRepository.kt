package com.nodelook.nativecli.data

import com.nodelook.nativecli.data.generated.allCommands
import com.nodelook.nativecli.data.generated.basicsData
import com.nodelook.nativecli.data.generated.commandNames
import com.nodelook.nativecli.data.generated.tipsData
import com.nodelook.shared.BasicInfo
import com.nodelook.shared.MarkdownParser
import com.nodelook.shared.TipInfo
import com.nodelook.shared.getSectionSortPriority

data class CommandInfo(val name: String)

data class BasicCategory(val id: String, val title: String)

data class CommandSection(val title: String, val content: String)

object DataRepository {

    fun getCommandNames(): List<String> = commandNames

    fun hasCommand(name: String): Boolean = allCommands.containsKey(name)

    fun getCommands(): List<CommandInfo> = commandNames.map { CommandInfo(it) }

    fun getCommandsByQuery(query: String): List<CommandInfo> {
        val lowerQuery = query.lowercase()
        return commandNames
            .filter { it.lowercase().contains(lowerQuery) }
            .sortedWith(
                compareBy(
                    { it.lowercase() != lowerQuery },
                    { !it.lowercase().startsWith(lowerQuery) },
                    { it },
                ),
            )
            .map { CommandInfo(it) }
    }

    fun getCommandSections(name: String): List<CommandSection> {
        val content = allCommands[name] ?: return emptyList()
        return MarkdownParser.splitByHeaders(content, "# ").map { (title, sectionContent) ->
            CommandSection(title, sectionContent)
        }.filter { it.title.uppercase() != "TAGLINE" }
            .sortedBy { getSectionSortPriority(it.title) }
    }

    fun getBasicCategories(): List<BasicCategory> = basicsData.mapNotNull { (id, content) ->
        val title = content.lines().firstOrNull { it.startsWith("# ") }
            ?.removePrefix("# ")?.trim()
        if (title != null) {
            BasicCategory(id = id, title = title)
        } else {
            null
        }
    }.sortedBy { it.title }

    fun getBasicInfo(categoryId: String): BasicInfo? {
        val content = basicsData[categoryId] ?: return null
        return MarkdownParser.parseBasic(content)
    }

    fun getTips(): List<TipInfo> = MarkdownParser.parseTips(tipsData)
}
