package hu.ubi.soft.basearchitect.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.ubi.soft.basearchitect.utils.UIActions
import hu.ubi.soft.basearchitect.utils.UIModel
import hu.ubi.soft.basearchitect.utils.UIStates
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<UM : UIModel, A : UIActions>(
    uiModelFactory: () -> UM,
    channelCapacity: Int = Channel.RENDEZVOUS
) : ViewModel() {

    var uiModel: UM = uiModelFactory()

    val channel: Channel<A> = Channel(channelCapacity)

    init {
        actionHandle { handleActions(it) }
    }

    abstract fun handleActions(action: A)

    fun addAction(action: A) {
        viewModelScope.launch {
            channel.send(action)
        }
    }

    private var _uiStates = MutableLiveData<UIStates>(UIStates.Init)
    val states: LiveData<UIStates> get() = _uiStates

    protected fun setLoadingState(action: A) {
        _uiStates.value = UIStates.Loading(action)
    }

    protected fun setError(message: String?, action: A) {
        _uiStates.value = UIStates.Error(message, action)
    }

    protected fun setSuccessState(action: A) {
        _uiStates.value = UIStates.Success(action)
    }

    internal fun setStateDone() {
        _uiStates.value = UIStates.StateDone
    }

}


fun <A : UIActions> BaseViewModel<*, A>.actionHandle(handler: (A) -> Unit) {
    viewModelScope.launch {
        channel.consumeAsFlow().collect {
            handler.invoke(it)
        }
    }
}