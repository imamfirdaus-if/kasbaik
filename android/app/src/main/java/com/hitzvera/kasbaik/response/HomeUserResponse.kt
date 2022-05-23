package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class HomeUserResponse(
	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
