package ru.kontur.users.api.repository

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import ru.kontur.users.api.model.User

@Component
@Primary
class UsersRepositoryEnricher(
    private val delegate: UsersRepository
) : UsersRepository by delegate {

    override suspend fun save(user: User): User {
        return delegate.save(
            user.copy(

            )
        )
    }
}