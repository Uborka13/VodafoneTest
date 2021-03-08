package hu.ubi.soft.vodafonetest.repositories

import hu.ubi.soft.vodafonetest.model.LoginResponse
import hu.ubi.soft.vodafonetest.network.Services
import hu.ubi.soft.vodafonetest.ui.fragment.dashboard.DashboardUIModel
import hu.ubi.soft.vodafonetest.ui.fragment.login.LoginUIModel
import hu.ubi.soft.vodafonetest.util.UserCredManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val services: Services,
    private val userCredManager: UserCredManager
) {

    fun postLogin(loginModel: LoginUIModel): Flow<LoginResponse> {
        return flow {
            val result = services.postLogin(loginModel.toLoginRequest())
            emit(result)
        }
    }

    fun saveCreds(loginName: String, accessToken: String?, refreshToken: String?) {
        userCredManager.saveUserName(loginName)
        userCredManager.saveAccessToken(
            accessToken ?: throw IllegalArgumentException("Missing access token")
        )
        userCredManager.saveRefreshToken(
            refreshToken ?: throw IllegalArgumentException("Missing refresh token")
        )
    }

    fun getUsername(): String {
        return userCredManager.getUserName() ?: throw IllegalArgumentException("Missing username")
    }

    fun hasValidLogin(): Boolean {
        return userCredManager.getUserName() != null && userCredManager.getAccessToken() != null && userCredManager.getRefreshToken() != null
    }

    fun logout() {
        userCredManager.logout()
    }

    fun getNetOffers(): Flow<DashboardUIModel> {
        return flow {
            val result = services.getNetOffers()
            emit(DashboardUIModel.fromNetOfferResponse(result))
        }
    }

}