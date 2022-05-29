package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class PostUpdataStatusResponse(

	@field:SerializedName("tabelmitra")
	val tabelmitra: List<Int?>? = null,

	@field:SerializedName("tabelborrower")
	val tabelborrower: List<Int?>? = null
)
