package software.shattered.datastore

import org.junit.jupiter.api.Test

class TestEmptyDataStore {
    @Test
    fun `should return nulls when not given a default`() {
        val dataStore = EmptyDataStore
        assert(dataStore.get("test", String::class.java) == null)
        assert(dataStore.get("test", Int::class.java) == null)
        assert(dataStore.get("test", Boolean::class.java) == null)
    }

    @Test
    fun `should return defaults when given defaults`() {
        val dataStore = EmptyDataStore
        assert(dataStore.getOrDef("test", "default") == "default")
        assert(dataStore.getOrDef("test", 1) == 1)
        assert(dataStore.getOrDef("test", true))
    }

    @Test
    fun `should return null from getUnsafe`() {
        val dataStore = EmptyDataStore
        assert(dataStore.getUnsafe("test") == null)
    }

    @Test
    fun `should keep track of no keys`() {
        val dataStore = EmptyDataStore
        assert(dataStore.keys.isEmpty())
    }
}