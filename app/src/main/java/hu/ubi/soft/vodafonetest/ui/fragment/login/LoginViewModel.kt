package hu.ubi.soft.vodafonetest.ui.fragment.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ubi.soft.basearchitect.utils.UIActions
import hu.ubi.soft.basearchitect.viewmodel.BaseViewModel
import hu.ubi.soft.vodafonetest.repositories.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
) :
    BaseViewModel<LoginUIModel, LoginActions>(::LoginUIModel) {
    override fun handleActions(action: LoginActions) {
        viewModelScope.launch {
            when (action) {
                is Login -> login(action)
            }
        }
    }

    private suspend fun login(action: Login) {
        setLoadingState(action)
        repository.postLogin(action.loginCredentials)
            .catch { setError(it.message, action) }
            .collect {
                repository.saveCreds(
                    action.loginCredentials.loginName,
                    it.accessToken,
                    it.refreshToken
                )
                setSuccessState(action)
            }
    }

}

sealed class LoginActions : UIActions
data class Login(val loginCredentials: LoginUIModel) : LoginActions()