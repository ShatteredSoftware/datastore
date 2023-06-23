package software.shattered.datastore

import org.junit.jupiter.api.Test

class TestGenericDataStore {
    @Test
    fun `should store and retrieve items`() {
        val store = GenericDataStore()
        store["test"] = "test"
        val result: String? = store["test"]
        assert(result == "test")
        // Should also allow explicitly getting the value
        assert(store.get("test", String::class.java) == "test")
    }

    @Test
    fun `should return nulls when not given a default`() {
        val dataStore = GenericDataStore()
        assert(dataStore.get("test", String::class.java) == null)
        assert(dataStore.get("test", Int::class.javaObjectType) == null)
        assert(dataStore.get("test", Boolean::class.java) == null)
    }

    @Test
    fun `should return defaults when given defaults`() {
        val dataStore = GenericDataStore()
        assert(dataStore.getOrDef("test", "default") == "default")
        assert(dataStore.getOrDef("test", 1) == 1)
        assert(dataStore.getOrDef("test", true))
    }

    @Test
    fun `should return defaults when given the wrong data type`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        assert(dataStore.getOrDef("test", 1) == 1)
        assert(dataStore["test", true])
    }

    @Test
    fun `should only return the proper type of data`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        assert(dataStore.get("test", String::class.java) == "test")
        assert(dataStore.getOrDef("test", 0) == 0)
        assert(dataStore.getOrDef("test", true))
    }

    @Test
    fun `should mask the data store`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        val masked: MaskedDataStore<MutableDataStore, String> = dataStore.masked()
        assert(masked["test"] == "test")
        assert(masked["test"] == "test")
        assert(masked["test2"] == null)
    }

    @Test
    fun `should clear properly`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        dataStore.clear()
        assert(dataStore.get("test", String::class.java) == null)
        assert(dataStore.get("test2", Int::class.javaObjectType) == null)
    }

    @Test
    fun `should remove items properly`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        dataStore.remove("test")
        assert(dataStore.get("test", String::class.java) == null)
        assert(dataStore.get("test2", Int::class.javaObjectType) == 5)
    }

    @Test
    fun `should add items if absent`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        dataStore.putIfAbsent("test", "nope")
        assert(dataStore.get("test", String::class.java) == "test")
    }

    @Test
    fun `should pull items unsafely`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        assert(dataStore.getUnsafe("test") == "test")
        assert(dataStore.getUnsafe("test2") == 5)
    }

    @Test
    fun `should keep track of keys`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        assert(dataStore.keys.contains("test"))
        assert(dataStore.keys.contains("test2"))
    }

    @Test
    fun `should keep track of values`() {
        val dataStore = GenericDataStore()
        dataStore["test"] = "test"
        dataStore["test2"] = 5
        assert(dataStore.values.contains("test"))
        assert(dataStore.values.contains(5))
    }

    @Test
    fun `should create stores from pairs`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        assert(dataStore.get("test", String::class.java) == "test")
        assert(dataStore.get("test2", Int::class.javaObjectType) == 5)
    }

    @Test
    fun `should convert to a map with asMapOf`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        val map = dataStore.asMapOf(String::class.java)
        assert(map["test"] == "test")
        assert(map["test2"] == null)

        val map2: Map<String, Int> = dataStore.asMapOf()
        assert(map2["test"] == null)
        assert(map2["test2"] == 5)
    }

    @Test
    fun `should allow setting values when masked`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        val masked: MaskedDataStore<MutableDataStore, String> = dataStore.masked()
        masked["test"] = "test2"
        assert(masked["test"] == "test2")
        assert(dataStore.get("test", String::class.java) == "test2")
    }

    @Test
    fun `should return the underlying source from masks`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        val masked: MaskedDataStore<MutableDataStore, String> = dataStore.masked()
        assert(masked.unmasked() == dataStore)
    }

    @Test
    fun `should merge data stores`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        val dataStore2 = GenericDataStore.of(
            "test" to "test2",
            "test3" to 6
        )

        val merged = dataStore.merge(dataStore2)
        assert(merged.get("test", String::class.java) == "test2")
        assert(merged.get("test2", Int::class.javaObjectType) == 5)
        assert(merged.get("test3", Int::class.javaObjectType) == 6)
    }

    @Test
    fun `should stringify`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        assert(DataStore.stringify(dataStore).values.contains("5"))
        assert(DataStore.stringify(dataStore).values.contains("test"))
    }

    @Test
    fun `should put values`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        dataStore.put("test", "test2")
        assert(dataStore.get("test", String::class.java) == "test2")
    }

    @Test
    fun `should pull from other stores`() {
        val dataStore = GenericDataStore.of(
            "test" to "test",
            "test2" to 5
        )

        val dataStore2 = GenericDataStore.of(
            "test" to "test2",
            "test3" to 6
        )

        dataStore.pullFrom(dataStore2)
        assert(dataStore.get("test", String::class.java) == "test2")
        assert(dataStore.get("test2", Int::class.javaObjectType) == 5)
        assert(dataStore.get("test3", Int::class.javaObjectType) == 6)
    }
}