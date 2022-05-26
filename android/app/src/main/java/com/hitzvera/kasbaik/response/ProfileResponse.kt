package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("usia")
	val usia: Int? = null,

	@field:SerializedName("credit_score")
	val creditScore: Int,

	@field:SerializedName("gender")
	val gender: Any? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("foto_selfie")
	val fotoSelfie: Any? = null,

	@field:SerializedName("foto_ktp")
	val fotoKtp: Any? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("alamat_tinggal")
	val alamatTinggal: String? = null,

	@field:SerializedName("profesi")
	val profesi: Int? = null,

	@field:SerializedName("alamat_ktp")
	val alamatKtp: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("foto_diri")
	val fotoDiri: Any? = null,

	@field:SerializedName("id_profile")
	val idProfile: String? = null
)
