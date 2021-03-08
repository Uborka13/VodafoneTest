package hu.ubi.soft.vodafonetest.ui.fragment.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ubi.soft.vodafonetest.repositories.Repository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun userHasValidLogin(): Boolean {
        return repository.hasValidLogin()
    }

}