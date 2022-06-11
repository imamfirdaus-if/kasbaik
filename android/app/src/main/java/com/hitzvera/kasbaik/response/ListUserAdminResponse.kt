package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class ListUserAdminResponse(

	@field:SerializedName("ListUserAdminResponse")
	val listUserAdminResponse: List<ListUserAdminResponseItem>
)

data class ListUserAdminResponseItem(

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
