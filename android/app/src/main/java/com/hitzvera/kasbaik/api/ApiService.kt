package com.hitzvera.kasbaik.api


import com.hitzvera.kasbaik.response.HomeUserResponse
import com.hitzvera.kasbaik.response.LoginResponse
import com.hitzvera.kasbaik.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    fun requestCreateAcountUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("role") role: String,
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("login")
    fun requestLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("home")
    fun requestHomePeminjam(
        @Header("Cookie") auth: String,
    ): Call<HomeUserResponse>



}