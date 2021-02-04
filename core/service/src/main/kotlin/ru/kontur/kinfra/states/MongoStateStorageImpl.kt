package ru.kontur.kinfra.states

import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.reactivestreams.client.MongoClient
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.bson.Document

class MongoStateStorageImpl(
    private val stateName: String,
    mongoClient: MongoClient,
    databaseName: String
) : StateStorage<String> {
    private val collection = mongoClient.getDatabase(databaseName).getCollection("states")

    override suspend fun getState(): String? {
        val filter = Filters.eq(ID_KEY, stateName)
        return collection.find(filter).awaitFirstOrNull()?.getString(STATE_KEY)
    }

    override suspend fun saveState(state: String) {
        val filter = Filters.eq("_id", stateName)
        val document = Document()
        document.append(ID_KEY, stateName)
        document.append(STATE_KEY, state)
        collection.replaceOne(filter, document, REPLACE_OPTIONS).awaitFirst()
    }

    private companion object {
        val REPLACE_OPTIONS = ReplaceOptions().upsert(true)
        const val ID_KEY = "_id"
        const val STATE_KEY = "state"
    }
}