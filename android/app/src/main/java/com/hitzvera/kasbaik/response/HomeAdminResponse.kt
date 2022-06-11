package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class HomeAdminResponse(

	@field:SerializedName("pending")
	val pending: Int,

	@field:SerializedName("borrower")
	val borrower: Int,

	@field:SerializedName("accepted")
	val accepted: Int
)
