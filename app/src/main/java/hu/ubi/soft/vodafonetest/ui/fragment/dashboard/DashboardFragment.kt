package hu.ubi.soft.vodafonetest.ui.fragment.dashboard

import androidx.fragment.app.viewModels
import hu.ubi.soft.basearchitect.fragment.BaseFragment
import hu.ubi.soft.basearchitect.utils.UIModel
import hu.ubi.soft.basearchitect.utils.viewBinding
import hu.ubi.soft.vodafonetest.databinding.FragmentDashboardBinding

class DashboardFragment : BaseFragment<DashboardUIModel, DashboardActions>() {
    override val viewModel by viewModels<DashboardViewModel>()
    override val binding by viewBinding(FragmentDashboardBinding::inflate)
}

data class DashboardUIModel(val id: String = "") : UIModel