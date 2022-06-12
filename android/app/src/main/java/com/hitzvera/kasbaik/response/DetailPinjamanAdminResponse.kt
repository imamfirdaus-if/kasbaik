package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class DetailPinjamanAdminResponse(

	@field:SerializedName("DetailPinjamanAdminResponse")
	val detailPinjamanAdminResponse: List<DetailPinjamanAdminResponseItem>
)

data class DetailPinjamanAdminResponseItem(

	@field:SerializedName("donasi")
	val donasi: Int,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("loan_amount")
	val loanAmount: Int,

	@field:SerializedName("monthly_income")
	val monthlyIncome: Int,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("reason_borrower")
	val reasonBorrower: String,

	@field:SerializedName("tenor")
	val tenor: Int,

	@field:SerializedName("pinjaman_ke")
	val pinjamanKe: Int,

	@field:SerializedName("dependents_amount")
	val dependentsAmount: Int,

	@field:SerializedName("credit_approval")
	val creditApproval: Int,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("payment_method")
	val paymentMethod: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
