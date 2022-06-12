package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class HistoryPaymentAdminResponse(

	@field:SerializedName("Table Payment")
	val tablePayment: List<TablePaymentItem>,

	@field:SerializedName("description")
	val description: List<DescriptionItem>
)

data class DescriptionItem(

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

data class TablePaymentItem(

	@field:SerializedName("payment_ke")
	val paymentKe: Int,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("amount_payment")
	val amountPayment: Int,

	@field:SerializedName("id_payment")
	val idPayment: String,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("payment_method")
	val paymentMethod: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
