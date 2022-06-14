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
        @Field("donasi") donasi: Int,
        @Field("credit_approval") creditApproval: Int,
    ): Call<PostBorrowerResponse>

    @DELETE("borrower/{id_borrower}")
    fun deleteRequestBorrowing(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<DeleteRequestBorrowingResponse>

    @FormUrlEncoded
    @PUT("borrower/{id_borrower}")
    fun editRequestBorrowing(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String,
        @Field("loan_amount") loanAmount: Int,
        @Field("reason_borrower") reasonBorrower: String,
        @Field("dependents_amount") dependentsAmount: Int,
        @Field("tenor") tenor: Int,
        @Field("monthly_income") monthlyIncome: Int,
        @Field("donasi") donasi: Int,
    ): Call<EditBorrowerResponse>

    @GET("updatestatus")
    fun getRequestBorrower(
        @Header("Cookie") auth: String
    ): Call<List<GetUpdateStatusResponseItem>>

    @FormUrlEncoded
    @POST("updatestatus/{id_borrower}")
    fun postRequestUpdateStatus(
        @Header("Cookie") auth: String,
        @Field("status") status: String,
        @Field("message") message: String,
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

    @GET("payment/{id_borrower}")
    fun getPaymentFromUser(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<GetPaymentResponseUser>

    @FormUrlEncoded
    @POST("payment/{id_borrower}")
    fun postPayment(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String,
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

    @GET("borrower/{id_borrower}")
    fun getBorrowing(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<GetBorrowerByIdResponse>

    @GET("messages")
    fun getAllMessages(
        @Header("Cookie") auth: String,
    ): Call<MessagesResponse>

    @FormUrlEncoded
    @POST("credit_approval")
    fun getCreditApproval(
        @Field("gender") gender: Int,
        @Field("usia") usia: Int,
        @Field("pinjaman") pinjaman: Int,
        @Field("tenor") tenor: Int,
        @Field("pemasukan") pemasukan: Int,
        @Field("tanggungan") tanggungan: Int,
        @Field("pekerjaan") pekerjaan: Int,
        @Field("donasi") donasi: Int,
    ): Call<CrediteApprovalResponse>

    @FormUrlEncoded
    @POST("credit_score")
    fun getCreditScore(
        @Field("usiakat") usiakat: Int,
        @Field("econkat") econkat: Int,
        @Field("pekerjaankat") pekerjaankat: Int,
        @Field("pinjamankekat") pinjamankekat: Int,
        @Field("telatharikat") telatharikat: Int,
        @Field("donasikat") donasikat: Int,
    ): Call<CrediteApprovalResponse>

    @GET("credit/{id_borrower}")
    fun getCreditData(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<CreditKatResponse>
    
    @GET("messages/{id_message}")
    fun updateHasReadMessage(
        @Header("Cookie") auth: String,
        @Path("id_message") idMessage: String
    ): Call<UpdateHasUpdateResponse>

    @FormUrlEncoded
    @POST("messages/{id_message}")
    fun updateBuktiBayar(
        @Header("Cookie") auth: String,
        @Path("id_message") idMessage: String,
        @Field("isAccepted") isAccepted: String,
    ): Call<MessagesResponse>

    @Multipart
    @POST("buktibayar")
    fun postBuktiBayar(
        @Header("Cookie") auth: String,
        @Part file: MultipartBody.Part,
        @Part("nominal") nominal: RequestBody,
        @Part("message") message: RequestBody,
    ): Call<UpdateHasUpdateResponse>

    @GET("buktibayar")
    fun getBuktiBayar(
        @Header("Cookie") auth: String
    ): Call<MessagesResponse>

    @GET("summary")
    fun getSummary(
        @Header("Cookie") auth: String
    ): Call<HomeAdminResponse>

    @GET("listakunmitra")
    fun getListMitra(
        @Header("Cookie") auth: String
    ): Call<List<GetListMitraResponseItem>>

    @GET("listakunuser")
    fun getListPengguna(
        @Header("Cookie") auth: String
    ): Call<List<ListUserAdminResponseItem>>

    @GET("listakunuser/{id_user}")
    fun getProfileUserAdmin(
        @Header("Cookie") auth: String,
        @Path("id_user") idUser: String
    ): Call<ProfileUserAdminResponse>

    @GET("listborrowerpending")
    fun getListBorrowerPending(
        @Header("Cookie") auth: String
    ): Call<List<ListBorrowerPendingResponseItem>>

    @GET("listborrower/{id_borrower}")
    fun getBorrowerByID(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<List<DetailPinjamanAdminResponseItem>>

    @GET("listpayment")
    fun getListPayment(
        @Header("Cookie") auth: String
    ): Call<List<ListPaymentAdminResponseItem>>

    @GET("listpayment/{id_borrower}")
    fun getPaymentByID(
        @Header("Cookie") auth: String,
        @Path("id_borrower") idBorrower: String
    ): Call<HistoryPaymentAdminResponse>

    @GET("summaryMitra/{id_mitra}")
    fun getSummaryMitra(
        @Header("Cookie") auth: String,
        @Path("id_mitra") idMitra: String,
    ): Call<SummaryMitraResponse>
}