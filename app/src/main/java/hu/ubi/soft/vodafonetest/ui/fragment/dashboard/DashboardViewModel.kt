package hu.ubi.soft.vodafonetest.ui.fragment.dashboard

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ubi.soft.basearchitect.utils.UIActions
import hu.ubi.soft.basearchitect.viewmodel.BaseViewModel
import hu.ubi.soft.vodafonetest.repositories.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) :
    BaseViewModel<DashboardUIModel, DashboardActions>(::DashboardUIModel) {

    override fun handleActions(action: DashboardActions) {
        viewModelScope.launch {
            when (action) {
                is GetNetOffers -> {
                    getNetOffers(action)
                }
                is InitFragment -> {
                    getNetOffers(action)

                }
                is GetUsername -> {
                    getUsername()
                }
                is Logout -> {
                    logout(action)
                }
            }
        }
    }

    private suspend fun getNetOffers(action: DashboardActions) {
        repository.getNetOffers()
            .onStart { setLoadingState(action) }
            .catch { setError(it.message, action) }
            .collect {
                uiModel = it
                setSuccessState(action)
            }
    }

    private fun getUsername() {
        uiModel.userName = repository.getUsername()
    }

    private fun logout(action: Logout) {
        repository.logout()
        setSuccessState(action)
    }

}

sealed class DashboardActions : UIActions
object InitFragment : DashboardActions()
object GetNetOffers : DashboardActions()
object GetUsername : DashboardActions()
object Logout : DashboardActions()