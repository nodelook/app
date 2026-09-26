package com.nodelook.app.data

import com.nodelook.shared.platform.PreferencesStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private class FakePreferencesStorage(initial: Map<String, Any> = emptyMap()) : PreferencesStorage {
    val data: MutableMap<String, Any> = initial.toMutableMap()

    override fun getString(key: String, defaultValue: String): String = (data[key] as? String) ?: defaultValue

    override fun putString(key: String, value: String) {
        data[key] = value
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = (data[key] as? Boolean) ?: defaultValue

    override fun putBoolean(key: String, value: Boolean) {
        data[key] = value
    }

    override fun getInt(key: String, defaultValue: Int): Int = (data[key] as? Int) ?: defaultValue

    override fun putInt(key: String, value: Int) {
        data[key] = value
    }
}

class DataManagerTest {

    // Stable id → name pairs from the legacy SQLite Command table.
    // These are spot-checked against the generated migration map.
    private val legacyXfsRepair = 0L to "xfs_repair"
    private val legacyZipinfo = 2L to "zipinfo"
    private val legacyEncfs = 7L to "encfs"

    @Test
    fun freshInstall_emptyAndFlagSet() {
        val prefs = FakePreferencesStorage()

        val dm = DataManager(prefs)

        assertEquals(emptySet(), dm.bookmarkNames.value)
        assertTrue(prefs.getBoolean(DataManager.KEY_BOOKMARKS_V1_MIGRATED, false))
    }

    @Test
    fun postV3_5User_v2Preserved_flagSet_v2Untouched() {
        val prefs = FakePreferencesStorage(
            mapOf(DataManager.KEY_BOOKMARKS_V2 to "ls,grep"),
        )

        val dm = DataManager(prefs)

        assertEquals(setOf("ls", "grep"), dm.bookmarkNames.value)
        assertTrue(prefs.getBoolean(DataManager.KEY_BOOKMARKS_V1_MIGRATED, false))
        assertEquals("ls,grep", prefs.getString(DataManager.KEY_BOOKMARKS_V2, ""))
    }

    @Test
    fun preV3_5User_legacyTranslatedAndPersisted() {
        val prefs = FakePreferencesStorage(
            mapOf(
                DataManager.KEY_BOOKMARKS_V1 to listOf(
                    legacyXfsRepair.first,
                    legacyZipinfo.first,
                ).joinToString(","),
            ),
        )

        val dm = DataManager(prefs)

        assertEquals(
            setOf(legacyXfsRepair.second, legacyZipinfo.second),
            dm.bookmarkNames.value,
        )
        assertTrue(prefs.getBoolean(DataManager.KEY_BOOKMARKS_V1_MIGRATED, false))
        assertEquals("", prefs.getString(DataManager.KEY_BOOKMARKS_V1, "missing"))
        // V2 must contain both names (order isn't guaranteed by Set).
        val savedV2 = prefs.getString(DataManager.KEY_BOOKMARKS_V2, "").split(",").toSet()
        assertEquals(setOf(legacyXfsRepair.second, legacyZipinfo.second), savedV2)
    }

    @Test
    fun hybridUser_unionsLegacyAndExistingV2() {
        val prefs = FakePreferencesStorage(
            mapOf(
                DataManager.KEY_BOOKMARKS_V1 to "${legacyXfsRepair.first}",
                DataManager.KEY_BOOKMARKS_V2 to "freshly_added",
            ),
        )

        val dm = DataManager(prefs)

        assertEquals(
            setOf(legacyXfsRepair.second, "freshly_added"),
            dm.bookmarkNames.value,
        )
        assertEquals("", prefs.getString(DataManager.KEY_BOOKMARKS_V1, "missing"))
        val savedV2 = prefs.getString(DataManager.KEY_BOOKMARKS_V2, "").split(",").toSet()
        assertEquals(setOf(legacyXfsRepair.second, "freshly_added"), savedV2)
    }

    @Test
    fun alreadyMigrated_skipsLegacyEvenIfPresent() {
        // Simulates a user who somehow has stale V1 data plus the migration flag.
        // The flag must short-circuit and V1 must be left untouched.
        val prefs = FakePreferencesStorage(
            mapOf(
                DataManager.KEY_BOOKMARKS_V1 to "${legacyXfsRepair.first}",
                DataManager.KEY_BOOKMARKS_V2 to "ls",
                DataManager.KEY_BOOKMARKS_V1_MIGRATED to true,
            ),
        )

        val dm = DataManager(prefs)

        assertEquals(setOf("ls"), dm.bookmarkNames.value)
        assertEquals(
            "${legacyXfsRepair.first}",
            prefs.getString(DataManager.KEY_BOOKMARKS_V1, ""),
        )
    }

    @Test
    fun legacyJunkInput_skippedSafelyWithoutCrashing() {
        // Mix of: valid id, unknown id, non-numeric, blank, negative.
        val prefs = FakePreferencesStorage(
            mapOf(
                DataManager.KEY_BOOKMARKS_V1 to listOf(
                    "${legacyEncfs.first}",
                    "999999999",
                    "not_a_number",
                    "",
                    "-1",
                ).joinToString(","),
            ),
        )

        val dm = DataManager(prefs)

        assertEquals(setOf(legacyEncfs.second), dm.bookmarkNames.value)
    }

    @Test
    fun migrationIsIdempotent_acrossInstances() {
        val prefs = FakePreferencesStorage(
            mapOf(
                DataManager.KEY_BOOKMARKS_V1 to "${legacyXfsRepair.first},${legacyZipinfo.first}",
            ),
        )

        val first = DataManager(prefs)
        val firstResult = first.bookmarkNames.value

        // Simulate a second app launch on the already-migrated prefs.
        val second = DataManager(prefs)
        val secondResult = second.bookmarkNames.value

        assertEquals(firstResult, secondResult)
        // V1 was cleared by the first run; second run must not have re-read or rewritten it.
        assertEquals("", prefs.getString(DataManager.KEY_BOOKMARKS_V1, "missing"))
    }

    @Test
    fun addAndRemoveBookmark_persistsToV2() {
        val prefs = FakePreferencesStorage()
        val dm = DataManager(prefs)

        dm.addBookmark("htop")
        dm.addBookmark("ls")
        assertTrue(dm.hasBookmark("htop"))
        assertTrue(dm.hasBookmark("ls"))
        val afterAdd = prefs.getString(DataManager.KEY_BOOKMARKS_V2, "").split(",").toSet()
        assertEquals(setOf("htop", "ls"), afterAdd)

        dm.removeBookmark("htop")
        assertFalse(dm.hasBookmark("htop"))
        val afterRemove = prefs.getString(DataManager.KEY_BOOKMARKS_V2, "").split(",").toSet()
        assertEquals(setOf("ls"), afterRemove)
    }
}
