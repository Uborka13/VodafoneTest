package hu.ubi.soft.vodafonetest.network

import hu.ubi.soft.vodafonetest.model.LoginRequest
import hu.ubi.soft.vodafonetest.model.NetOffersResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Services {

    @POST("/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): ResponseBody

    @GET("/net/offers")
    suspend fun getNetOffers(): NetOffersResponse
}