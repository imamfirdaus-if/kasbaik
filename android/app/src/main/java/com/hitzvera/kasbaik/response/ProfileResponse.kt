package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("alamat_ktp")
	val alamatKtp: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("foto_selfie")
	val fotoSelfie: String? = null,

	@field:SerializedName("no_wa")
	val noWa: String? = null,

	@field:SerializedName("foto_diri")
	val fotoDiri: String? = null,

	@field:SerializedName("foto_ktp")
	val fotoKtp: String? = null,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("alamat_tinggal")
	val alamatTinggal: String? = null,

	@field:SerializedName("profesi")
	val profesi: String? = null,

	@field:SerializedName("id_profile")
	val idProfile: String
)
