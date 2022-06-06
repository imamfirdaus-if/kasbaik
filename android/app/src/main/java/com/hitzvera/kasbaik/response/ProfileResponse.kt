package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class Profile(
	@field:SerializedName("profile")
	val profile: ProfileResponse,

	@field:SerializedName("message")
	val message: List<MessageItem>
)
data class MessageItem(
	@field:SerializedName("id_message")
	val idMessage: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("id_mitra")
	val idMitra: String?=null,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("has_read")
	val hasRead: Boolean,

	@field:SerializedName("link_bukti")
	val linkBukti: String?=null,

	@field:SerializedName("nominal")
	val nominal: Int? = null,

	@field:SerializedName("isAccepted")
	val isAccepted: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,
)

data class ProfileResponse(

	@field:SerializedName("usia")
	val usia: Int? = null,

	@field:SerializedName("credit_score")
	val creditScore: Int,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("foto_selfie")
	val fotoSelfie: String? = null,

	@field:SerializedName("foto_ktp")
	val fotoKtp: String? = null,

	@field:SerializedName("id_user")
	val idUser: String? = null,

	@field:SerializedName("alamat_tinggal")
	val alamatTinggal: String? = null,

	@field:SerializedName("profesi")
	val profesi: String? = null,

	@field:SerializedName("alamat_ktp")
	val alamatKtp: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("foto_diri")
	val fotoDiri: String? = null,

	@field:SerializedName("id_profile")
	val idProfile: String? = null,

)
