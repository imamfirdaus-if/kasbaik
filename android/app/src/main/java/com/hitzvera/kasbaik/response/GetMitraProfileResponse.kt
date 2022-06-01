package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class GetMitraProfileResponse(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("partner_name")
	val partnerName: String,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("foto_profile")
	val fotoProfile: String? = null,

	@field:SerializedName("location_mitra")
	val locationMitra: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
