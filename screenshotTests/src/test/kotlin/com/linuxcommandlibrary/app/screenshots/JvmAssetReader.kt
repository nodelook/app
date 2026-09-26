package com.nodelook.app.screenshots

import com.nodelook.shared.platform.AssetReader
import java.io.File

class JvmAssetReader(private val assetsRoot: File) : AssetReader {

    override fun listFiles(path: String): List<String> {
        val indexFile = File(assetsRoot, "$path/index.txt")
        if (indexFile.exists()) {
            return indexFile.readLines().map { it.trim() }.filter { it.isNotEmpty() }
        }
        val dir = File(assetsRoot, path)
        if (!dir.isDirectory) return emptyList()
        return dir.listFiles()?.map { it.name }.orEmpty()
    }

    override fun readFile(path: String): String? {
        val file = File(assetsRoot, path)
        return if (file.exists()) file.readText() else null
    }
}

fun resolveAssetsRoot(): File {
    var dir: File? = File(System.getProperty("user.dir"))
    while (dir != null) {
        val candidate = File(dir, "assets")
        if (File(candidate, "tips.md").exists()) return candidate
        dir = dir.parentFile
    }
    error("Could not locate assets/ directory walking up from ${System.getProperty("user.dir")}")
}
