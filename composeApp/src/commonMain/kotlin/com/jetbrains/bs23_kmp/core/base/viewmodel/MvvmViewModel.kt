package com.jetbrains.bs23_kmp.core.base.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class MvvmViewModel : ViewModel() {
//    val _showToast = MutableLiveData<SingleEvent<Any>>()
//    val showToast: LiveData<SingleEvent<Any>> get() = _showToast
//
//    fun showToast(message: String) {
//        _showToast.postValue( SingleEvent(message))
//    }

    private val _showToast = MutableSharedFlow<String>()
    val showToast: Flow<String> get() = _showToast.asSharedFlow()

    fun showToast(message: String) {
        viewModelScope.launch {
            _showToast.emit(message)
        }
    }


    private val handler = CoroutineExceptionHandler { _, exception ->
//        Timber.tag(SAFE_LAUNCH_EXCEPTION).e(exception)
        handleError(exception)

    }

    open fun handleError(exception: Throwable) {}

    open fun startLoading() {}

    protected fun safeLaunch( disableStartLoading: Boolean = false, block: suspend CoroutineScope.() -> Unit) {
        if(!disableStartLoading) startLoading()
        viewModelScope.launch(context = handler, block = block)

    }


    /** USE CASE
     * in call site, you should write something like this
     *
     fetchMultipleResource(
        { apiCall1() },
        { apiCall2() }
     ){
        data = data.copy(
            res1 = it[0].cast<Res1>(),
            res2 = it[1].cast<Res2>()
        )
     }
     **/


    protected fun <T> fetchMultipleResource(
        vararg apiCalls: suspend () -> T,
        completionHandler: (List<T>) -> Unit = {}
    ){
        startLoading()
        safeLaunchIO{
            val results = apiCalls.map {
                    async { it.invoke() }
                }.map { it.await() }
            completionHandler.invoke(results)
        }
    }


    protected fun safeLaunchIO(
        completionHandler: () -> Unit = {},
        block: suspend CoroutineScope.() -> Unit,
        ) {
//        startLoading()
        viewModelScope.launch(context = handler, block = {
            withContext(Dispatchers.IO) {
                block.invoke(this)
            }
        }).invokeOnCompletion {
            completionHandler.invoke()
        }
    }


    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { handleError(it) }
            .collect {
                completionHandler.invoke(it)
            }
    }

//    protected suspend fun <T> execute(
//        callFlow: Flow<DataState<T>>,
//        completionHandler: (collect: T) -> Unit = {}
//    ) {
//        callFlow
//            .onStart { startLoading() }
//            .catch { handleError(it) }
//            .collect { state ->
//                when (state) {
//                    is DataState.Error -> handleError(state.error)
//                    is DataState.Success -> completionHandler.invoke(state.result)
//                }
//            }
//    }

    companion object {
        private const val SAFE_LAUNCH_EXCEPTION = "ViewModel-ExceptionHandler"
    }
}