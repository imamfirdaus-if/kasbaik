package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class PostBorrowerResponse(

	@field:SerializedName("hasilcreds")
	val hasilcreds: Hasilcreds,

	@field:SerializedName("borrower")
	val borrower: Borrower,

	@field:SerializedName("mitradata")
	val mitradata: Mitradata
)

data class Borrower(

	@field:SerializedName("donasi")
	val donasi: Int? = null,

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

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("payment_method")
	val paymentMethod: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("credit_approval")
	val creditApproval: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Hasilcreds(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("usiakat")
	val usiakat: Int,

	@field:SerializedName("profesikat")
	val profesikat: Int,

	@field:SerializedName("econkat")
	val econkat: Int,

	@field:SerializedName("telatkat")
	val telatkat: Int? = null,

	@field:SerializedName("pinjamankat")
	val pinjamankat: Int,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("donasikat")
	val donasikat: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class Mitradata(

	@field:SerializedName("usia")
	val usia: Int,

	@field:SerializedName("donasi")
	val donasi: Int? = null,

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

	@field:SerializedName("dependents_amount")
	val dependentsAmount: Int,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
