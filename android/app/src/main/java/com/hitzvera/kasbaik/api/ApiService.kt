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
    ): Call<Profile>

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
    ): Call<Profile>

    @FormUrlEncoded
    @POST("borrower")
    fun postRequestBorrwer(
        @Header("Cookie") auth: String,
        @Field("loan_amount") loanAmount: Int,
        @Field("reason_borrower") reasonBorrower: String,
        @Field("monthly_income") monthlyIncome: Int,
        @Field("payment_method") paymentMethod: String,
        @Field("tenor") tenor: Int,
        @Field("dependents_amount") dependentsAmount: Int,
        @Field("donasi") donasi: Int
    ): Call<PostBorrowerResponse>

    @GET("updatestatus")
    fun getRequestBorrower(
        @Header("Cookie") auth: String
    ): Call<List<GetUpdateStatusResponseItem>>

    @FormUrlEncoded
    @POST("updatestatus/{id_borrower}")
    fun postRequestUpdateStatus(
        @Header("Cookie") auth: String,
        @Field("status") status: String,
        @Path("id_borrower") idBorrower: String,
    ): Call<PostUpdataStatusResponse>

    @GET("mitraprof")
    fun getRequestMitraProfile(
        @Header("Cookie") auth: String,
    ): Call<GetMitraProfileResponse>

    @Multipart
    @POST("mitraprof")
    fun postRequestMitraProf(
        @Header("Cookie") auth: String,
        @Part file: MultipartBody.Part,
        @Part("partner_name") partnerName: RequestBody,
        @Part("location_mitra") locationMitra: RequestBody,
        @Part("phone") phone: RequestBody,
    ): Call<GetMitraProfileResponse>

    @GET("payment")
    fun getPayment(
        @Header("Cookie") auth: String,
        @Query("id_borrower") idBorrower: String
    ): Call<PaymentResponse>

    @GET("payment/{id_borrower}")
    fun getPaymentById(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<GetPaymentResponseUser>

    @GET("payment")
    fun getPaymentFromUser(
        @Header("Cookie") auth: String,
        @Query("id_borrower") idBorrower: String
    ): Call<GetPaymentResponseUser>

    @FormUrlEncoded
    @POST("payment")
    fun postPayment(
        @Header("Cookie") auth: String,
        @Field("id_borrower") idBorrower: String,
        @Field("payment_method") paymentMethod: String,
        @Field("amount_payment") amountPayment: Int
    ): Call<PaymentResponse>

    @GET("borrower")
    fun getRequestBorrowing(
        @Header("Cookie") auth: String
    ): Call<List<Borrower>>

    @GET("borrower")
    fun getRiwayat(
        @Header("Cookie") auth: String,
        @Query("status") status: String
    ) : Call<List<Borrower>>

    @GET("borrower")
    fun getBorrowing(
        @Header("Cookie") auth: String,
        @Query("pinjaman_ke") pinjamanKe: String
    ): Call<List<Borrower>>
}