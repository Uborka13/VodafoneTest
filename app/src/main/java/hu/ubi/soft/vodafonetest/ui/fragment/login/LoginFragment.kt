package hu.ubi.soft.vodafonetest.ui.fragment.login

import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import hu.ubi.soft.basearchitect.fragment.BaseFragment
import hu.ubi.soft.basearchitect.utils.UIModel
import hu.ubi.soft.basearchitect.utils.viewBinding
import hu.ubi.soft.vodafonetest.databinding.FragmentLoginBinding
import hu.ubi.soft.vodafonetest.model.LoginRequest

class LoginFragment : BaseFragment<LoginUIModel, LoginActions>() {
    override val viewModel by viewModels<LoginViewModel>()
    override val binding: ViewBinding by viewBinding(FragmentLoginBinding::inflate)



}

data class LoginUIModel(val loginName: String = "", val password: String = "") : UIModel {
    fun toLoginRequest(): LoginRequest {
        return LoginRequest(loginName, password)
    }
}