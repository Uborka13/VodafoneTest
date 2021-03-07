package hu.ubi.soft.vodafonetest.ui.fragment.dashboard

import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ubi.soft.basearchitect.utils.UIActions
import hu.ubi.soft.basearchitect.viewmodel.BaseViewModel
import hu.ubi.soft.vodafonetest.repositories.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: Repository) : BaseViewModel<DashboardUIModel, DashboardActions>(::DashboardUIModel) {

    override fun handleActions(action: DashboardActions) {
        when(action) {
            is SelectNetOption -> { }
            GetNetOffers -> { }
        }
    }

    suspend fun getNetOffers() {
        repository.getNetOffers()
            .catch {  }
            .collect {  }
    }
}

sealed class DashboardActions: UIActions
object GetNetOffers : DashboardActions()
data class SelectNetOption(val option: String): DashboardActions()