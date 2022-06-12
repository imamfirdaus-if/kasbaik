package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class ListBorrowerPendingResponse(

	@field:SerializedName("ListBorrowerPendingResponse")
	val listBorrowerPendingResponse: List<ListBorrowerPendingResponseItem>
)

data class ListBorrowerPendingResponseItem(

	@field:SerializedName("usia")
	val usia: Int,

	@field:SerializedName("donasi")
	val donasi: Int,

	@field:SerializedName("credit_score")
	val creditScore: Int,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("loan_amount")
	val loanAmount: Int,

	@field:SerializedName("monthly_income")
	val monthlyIncome: Int,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("profesi")
	val profesi: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("reason_borrower")
	val reasonBorrower: String,

	@field:SerializedName("tenor")
	val tenor: Int,

	@field:SerializedName("pinjaman_ke")
	val pinjamanKe: Int,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("dependents_amount")
	val dependentsAmount: Int,

	@field:SerializedName("credit_approval")
	val creditApproval: Int,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
