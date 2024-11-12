package ru.urfu.consecutivepractice.coroutinesUtils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun CoroutineScope.launchLoadingAndError(
    handleError: (Throwable) -> Unit,
    updateLoading: (Boolean) -> Unit,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val context = CoroutineExceptionHandler { _, throwable -> handleError.invoke(throwable) }

    val loadingHandler = LoadingContextHandler(updateLoading)

    return launch(context + loadingHandler) {
        try {
            loadingHandler.showProgress()
            block()
        } finally {
            loadingHandler.hideProgress()
        }
    }
}

class LoadingContextHandler(
    private val updateLoading: (Boolean) -> Unit
) : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key

    companion object Key : CoroutineContext.Key<LoadingContextHandler>

    fun showProgress() = updateLoading.invoke(true)
    fun hideProgress() = updateLoading.invoke(false)
}
