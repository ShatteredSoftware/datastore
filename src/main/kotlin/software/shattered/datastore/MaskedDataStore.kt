package software.shattered.datastore

/**
 * Represents a data store that is masked, only returning types of data
 * that match the given type.
 */
class MaskedDataStore<Underlying : MutableDataStore, T : Any>(
    private val cl: Class<T>,
    private val dataStore: Underlying
) {
    operator fun get(id: String): T? {
        return dataStore.get(id, cl)
    }

    operator fun set(id: String, value: T) {
        dataStore.put(id, value)
    }

    fun unmasked(): Underlying {
        return dataStore
    }
}