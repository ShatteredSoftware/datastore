package software.shattered.datastore

/**
 * Represents an empty data store, never returning anything.
 */
object EmptyDataStore : DataStore {
    override val keys: Set<String>
        get() = emptySet()

    override fun <T : Any> get(id: String, cl: Class<T>): T? {
        return null
    }

    override fun <T : Any> getOrDef(id: String, def: T): T {
        return def
    }

    override fun getUnsafe(id: String): Any? {
        return null
    }
}
