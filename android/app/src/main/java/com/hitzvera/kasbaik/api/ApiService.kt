package com.hitzvera.kasbaik.api


import com.hitzvera.kasbaik.response.LoginResponse
import com.hitzvera.kasbaik.response.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("signup")
    fun requestCreateAcountUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
    ): Call<UserResponse>

    @FormUrlEncoded
    @POST("login")
    fun requestLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>



}