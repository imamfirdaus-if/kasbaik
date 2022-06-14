package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class GetPaymentResponseUser(

	@field:SerializedName("payment")
	val payment: List<PaymentItem>
)

data class PaymentItem(

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

	@field:SerializedName("payment_ke")
	val paymentKe: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
