package com.hitzvera.kasbaik.api


import com.hitzvera.kasbaik.response.*
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

    @FormUrlEncoded
    @POST("borrower")
    fun postRequestBorrwer(
        @Header("Cookie") auth: String,
        @Field("loan_amount") loanAmount: Int,
        @Field("reason_borrower") reasonBorrower: String,
        @Field("monthly_income") monthlyIncome: Int,
        @Field("payment_method") paymentMethod: String,
        @Field("tenor") tenor: Int,
        @Field("dependents_amount") dependentsAmount: Int
    ): Call<PostBorrowerResponse>

    @GET("updatestatus")
    fun getRequestBorrower(
        @Header("Cookie") auth: String
    ): Call<List<GetUpdateStatusResponseItem>>
}