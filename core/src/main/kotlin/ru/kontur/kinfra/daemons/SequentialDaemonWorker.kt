package ru.kontur.kinfra.daemons

import kotlinx.coroutines.CoroutineScope

abstract class SequentialDaemonWorker : AbstractDaemonWorker() {

    protected abstract suspend fun handle()

    override suspend fun run(scope: CoroutineScope) {
        loop {
            handle()
        }
    }
}