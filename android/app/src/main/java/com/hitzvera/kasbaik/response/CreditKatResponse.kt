package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class CreditKatResponse(

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("usiakat")
	val usiakat: Int,

	@field:SerializedName("pemasukanstr")
	val pemasukanstr: Double,

	@field:SerializedName("profesikat")
	val profesikat: Int,

	@field:SerializedName("pinjamankekat")
	val pinjamankekat: Int,

	@field:SerializedName("telatkat")
	val telatkat: Int?=null,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("donasistr")
	val donasistr: Int,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("genderkat")
	val genderkat: Int,

	@field:SerializedName("usiastr")
	val usiastr: Double,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("econkat")
	val econkat: Int,

	@field:SerializedName("tenorstr")
	val tenorstr: Double,

	@field:SerializedName("profesistr")
	val profesistr: Int,

	@field:SerializedName("pinjamanstr")
	val pinjamanstr: Double,

	@field:SerializedName("tanggunganstr")
	val tanggunganstr: Double,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("donasikat")
	val donasikat: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
