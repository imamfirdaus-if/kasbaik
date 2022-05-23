package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("alamat_ktp")
	val alamatKtp: Any? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: Any? = null,

	@field:SerializedName("foto_selfie")
	val fotoSelfie: Any? = null,

	@field:SerializedName("id_users")
	val idUsers: String? = null,

	@field:SerializedName("no_wa")
	val noWa: String? = null,

	@field:SerializedName("foto_diri")
	val fotoDiri: Any? = null,

	@field:SerializedName("foto_ktp")
	val fotoKtp: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("alamat_tinggal")
	val alamatTinggal: Any? = null,

	@field:SerializedName("profesi")
	val profesi: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
