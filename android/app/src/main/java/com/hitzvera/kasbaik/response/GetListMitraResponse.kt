package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class GetListMitraResponse(

	@field:SerializedName("GetListMitraResponse")
	val getListMitraResponse: List<GetListMitraResponseItem>
)

data class GetListMitraResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
