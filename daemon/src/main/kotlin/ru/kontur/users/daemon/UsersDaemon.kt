package ru.kontur.users.daemon

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.kontur.users.daemon.worker.UsersDaemonWorker

@SpringBootApplication
class UsersDaemon(
    private val usersDaemonWorker: UsersDaemonWorker
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        usersDaemonWorker.start()
    }
}


fun main(args: Array<String>) {
    runApplication<UsersDaemon>(*args)
}