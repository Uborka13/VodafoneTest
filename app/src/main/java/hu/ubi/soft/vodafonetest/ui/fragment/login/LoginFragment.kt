package hu.ubi.soft.vodafonetest.ui.fragment.login

import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.ubi.soft.basearchitect.fragment.BaseFragment
import hu.ubi.soft.basearchitect.utils.UIModel
import hu.ubi.soft.basearchitect.utils.viewBinding
import hu.ubi.soft.vodafonetest.R
import hu.ubi.soft.vodafonetest.databinding.FragmentLoginBinding
import hu.ubi.soft.vodafonetest.extensions.hideIf
import hu.ubi.soft.vodafonetest.extensions.onClick
import hu.ubi.soft.vodafonetest.extensions.showError
import hu.ubi.soft.vodafonetest.extensions.showIf
import hu.ubi.soft.vodafonetest.model.LoginRequest

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginUIModel, LoginActions>() {
    override val viewModel by viewModels<LoginViewModel>()
    override val binding by viewBinding(FragmentLoginBinding::inflate)

    override fun initUI() {
        super.initUI()
        binding.tvTandC.onClick {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToChromeTabFragment(
                    "https://youtu.be/dQw4w9WgXcQ"
                )
            )
        }
        binding.mbLogin.onClick {
            if (isFormValid()) {
                viewModel.addAction(
                    Login(
                        LoginUIModel(
                            binding.tfLoginName.textFieldValue ?: "",
                            binding.tfLoginPassword.textFieldValue ?: ""
                        )
                    )
                )
            }
        }
        binding.tfLoginName.textFieldValue = "Test"
        binding.tfLoginPassword.textFieldValue = "Test1234"
    }

    override fun onLoading(action: LoginActions) {
        binding.pbLoading.showIf(action is Login)
        binding.mbLogin.hideIf(action is Login)
    }

    override fun onSuccess(action: LoginActions) {
        binding.pbLoading.hideIf(action is Login)
        binding.mbLogin.showIf(action is Login)
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
    }

    override fun onError(error: String?, action: LoginActions) {
        binding.pbLoading.hideIf(action is Login)
        binding.mbLogin.showIf(action is Login)
        showError(error ?: "Hiba történt")
    }

    private fun isFormValid(): Boolean {
        var isValid = true
        if (!binding.tfLoginName.isValid()) {
            binding.tfLoginName.displayError(getString(R.string.lbl_login_name_error_text))
            isValid = false
        }
        if (!binding.tfLoginPassword.isValid()) {
            binding.tfLoginPassword.displayError(getString(R.string.lbl_password_error_text))
            isValid = false
        }
        return isValid
    }


}

data class LoginUIModel(val loginName: String = "", val password: String = "") : UIModel {
    fun toLoginRequest(): LoginRequest {
        return LoginRequest(loginName, password)
    }
}