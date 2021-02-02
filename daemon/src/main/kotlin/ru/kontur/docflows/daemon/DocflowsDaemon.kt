package ru.kontur.docflows.daemon

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.kontur.docflows.daemon.worker.DocflowsDaemonWorker

@SpringBootApplication
class DocflowsDaemon(
    private val docflowsDaemonWorker: DocflowsDaemonWorker
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        docflowsDaemonWorker.start()
    }
}


fun main(args: Array<String>) {
    runApplication<DocflowsDaemon>(*args)
}