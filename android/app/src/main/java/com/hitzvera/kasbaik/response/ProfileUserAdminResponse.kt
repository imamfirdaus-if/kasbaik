package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class ProfileUserAdminResponse(

	@field:SerializedName("profiles")
	val profiles: List<ProfilesItem>,

	@field:SerializedName("messages")
	val messages: List<MessagesItem>
)

data class MessagesItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("nominal")
	val nominal: Any,

	@field:SerializedName("id_message")
	val idMessage: String,

	@field:SerializedName("isAccepted")
	val isAccepted: Any,

	@field:SerializedName("link_bukti")
	val linkBukti: Any,

	@field:SerializedName("id_borrower")
	val idBorrower: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("id_mitra")
	val idMitra: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("has_read")
	val hasRead: Boolean,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class ProfilesItem(

	@field:SerializedName("usia")
	val usia: Int,

	@field:SerializedName("credit_score")
	val creditScore: Int,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("foto_selfie")
	val fotoSelfie: String,

	@field:SerializedName("foto_ktp")
	val fotoKtp: String,

	@field:SerializedName("id_user")
	val idUser: String,

	@field:SerializedName("alamat_tinggal")
	val alamatTinggal: String,

	@field:SerializedName("profesi")
	val profesi: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("alamat_ktp")
	val alamatKtp: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("foto_diri")
	val fotoDiri: String,

	@field:SerializedName("id_profile")
	val idProfile: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
