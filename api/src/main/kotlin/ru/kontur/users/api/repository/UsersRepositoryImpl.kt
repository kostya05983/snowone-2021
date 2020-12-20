package ru.kontur.users.api.repository

import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Component
import ru.kontur.users.api.model.User

@Component
class UsersRepositoryImpl(
    private val template: ReactiveMongoTemplate
) : UsersRepository {
    override suspend fun save(user: User): User {
        return template.save(user).awaitFirst()
    }

    override suspend fun get(id: String): User? {
        val criteria = Criteria.where(User::id.name).isEqualTo(id)
        val query = Query().addCriteria(criteria)

        return template.find(query, User::class.java).awaitFirstOrNull()
    }
}