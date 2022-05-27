package com.hitzvera.kasbaik.api


import com.hitzvera.kasbaik.response.HomeUserResponse
import com.hitzvera.kasbaik.response.LoginResponse
import com.hitzvera.kasbaik.response.ProfileResponse
import com.hitzvera.kasbaik.response.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

//    @GET("home")
//    fun requestHomePeminjam(
//        @Header("Cookie") auth: String,
//    ): Call<HomeUserResponse>

    @GET("profile")
    fun getRequestProfile(
        @Header("Cookie") auth: String,
    ): Call<ProfileResponse>

    @Multipart
    @POST("profile")
    fun postRequestProfile(
        @Header("Cookie") auth: String,
        @Part file1: MultipartBody.Part,
        @Part file2: MultipartBody.Part,
        @Part file3: MultipartBody.Part,
        @Part("usia") usia: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("alamat_tinggal") alamatTinggal: RequestBody,
        @Part("alamat_ktp") alamatKtp: RequestBody,
        @Part("profesi") profesi: RequestBody,
    ): Call<ProfileResponse>
}