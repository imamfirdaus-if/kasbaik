package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class ListPaymentAdminResponse(

	@field:SerializedName("ListPaymentAdminResponse")
	val listPaymentAdminResponse: List<ListPaymentAdminResponseItem>
)

data class ListPaymentAdminResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("loan_amount")
	val loanAmount: Int,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("id_mitra")
	val idMitra: Any,

	@field:SerializedName("target_lunas")
	val targetLunas: String,

	@field:SerializedName("total_payment")
	val totalPayment: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
