package hu.ubi.soft.vodafonetest.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("jwt")
    val jwt: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)