package com.crypto.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException
import java.net.SocketException

open class BaseViewModel : ViewModel() {

    protected val attachedScope: CoroutineScope by lazy {
        createCoroutineScope()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        if (e !is IOException || e !is SocketException || e !is InterruptedException) {
            Timber.w(
                e,
                "Undeliverable exception received by coroutine, not sure what to do"
            )
        }
    }

    private fun createCoroutineScope(): CoroutineScope {
        return CoroutineScope(
            SupervisorJob() + Dispatchers.Main.immediate + coroutineExceptionHandler
        )
    }

    override fun onCleared() {
        super.onCleared()
        attachedScope.cancel()
    }
}