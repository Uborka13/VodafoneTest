package hu.ubi.soft.vodafonetest.repositories

import hu.ubi.soft.vodafonetest.network.Services
import hu.ubi.soft.vodafonetest.ui.fragment.login.LoginUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

class Repository @Inject constructor(private val services: Services) {

    fun postLogin(loginModel: LoginUIModel): Flow<ResponseBody> {
        return flow {
            val result = services.postLogin(loginModel.toLoginRequest())
            emit(result)
        }
    }

    fun getNetOffers(): Flow<Any> {
        return flow {
            val result = services.getNetOffers()
            emit(result)
        }
    }

}