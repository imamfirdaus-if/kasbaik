package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityProfileBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
import com.hitzvera.kasbaik.utils.reduceFileImage
import com.hitzvera.kasbaik.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun Boolean.toInt() = if (this) 1 else 0
class ProfileActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private var getFile1: File? = null
    private var getFile2: File? = null
    private var getFile3: File? = null
    private var linkImage1: String? = null
    private var linkImage2: String? = null
    private var linkImage3: String? = null
    private var fileChosen: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val token = intent.getStringExtra(HomePeminjamActivity.TOKEN)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.getProfile(this, token!!)
        viewModel.profileResponse.observe(this){
            if(it!=null){
                binding.apply {
                    tvName.setText(it.namaLengkap)
                    tvWhatsapp.setText(it.phone)
                    edPekerjaan.setText(it.profesi ?: "")
                    Glide.with(this@ProfileActivity)
                        .load(it.fotoDiri)
                        .fallback(R.drawable.avatar_placeholder)
                        .error(R.drawable.avatar_placeholder)
                        .placeholder(R.drawable.avatar_placeholder)
                        .into(ivAvatar)
                    edAlamatTinggal.setText(it.alamatTinggal ?: "isi alamat tinggal")
                    edAlamatKtp.setText(it.alamatKtp ?: "isi alamat di ktp")
                    edUsia.setText("${it.usia}")
                    if(it.fotoDiri.toString().isNotBlank()){
                        linkImage1 = it.fotoDiri
                        linkImage2 = it.fotoKtp
                        linkImage3 = it.fotoSelfie
                    }
                }
                if(it.alamatTinggal.isNullOrBlank()){
                    val dialog = Dialog(this)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(true)
                    dialog.setContentView(R.layout.pop_up_command)
                    val btnIsi: Button = dialog.findViewById(R.id.btn_isi)
                    btnIsi.setOnClickListener { dialog.dismiss() }
                    dialog.show()
                }
            }
        }
        binding.btnSave.setOnClickListener(this)
        binding.btnChoose1.setOnClickListener(this)
        binding.btnChoose2.setOnClickListener(this)
        binding.btnChoose3.setOnClickListener(this)
        binding.seeImage1.setOnClickListener(this)
        binding.seeImage2.setOnClickListener(this)
        binding.seeImage3.setOnClickListener(this)
    }


    private fun startGallery(number: Int) {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
        fileChosen = number
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@ProfileActivity)
            when (fileChosen) {
                1 -> {
                    getFile1 = myFile
                    binding.tvLabelFotoDiri.text = reduceFileImage(getFile1 as File).name
                }
                2 -> {
                    getFile2 = myFile
                    binding.tvLabelFotoKtp.text = reduceFileImage(getFile2 as File).name
                }
                else -> {
                    getFile3 = myFile
                    binding.tvLabelFotoSelfie.text = reduceFileImage(getFile3 as File).name
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_save -> {
                postProfile(intent.getStringExtra(HomePeminjamActivity.TOKEN)!!)
            }
            R.id.btn_choose_1 -> {
                startGallery(1)
            }
            R.id.btn_choose_2 -> {
                startGallery(2)
            }
            R.id.btn_choose_3 -> {
                startGallery(3)
            }
            R.id.see_image_1 -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.see_image)
                val ivImage: ImageView = dialog.findViewById(R.id.image)
                Glide.with(this).load(linkImage1).into(ivImage)
                dialog.show()
            }
            R.id.see_image_2 -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.see_image)
                val ivImage: ImageView = dialog.findViewById(R.id.image)
                Glide.with(this).load(linkImage2).into(ivImage)
                dialog.show()
            }
            R.id.see_image_3 -> {
                val dialog = Dialog(this)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.see_image)
                val ivImage: ImageView = dialog.findViewById(R.id.image)
                Glide.with(this).load(linkImage3).into(ivImage)
                dialog.show()
            }
        }
    }

    private fun validateForm(): Boolean{
        return (binding.edUsia.text.isNotEmpty()
                && !TextUtils.isEmpty(binding.edPekerjaan.text)
                && getFile1 != null
                && getFile2 != null
                && getFile3 != null
                && binding.edAlamatTinggal.text.isNotEmpty()
                && binding.edAlamatKtp.text.isNotEmpty())
    }
    private fun postProfile(
        token: String,
    ){
        if(validateForm()){
            val file1 = reduceFileImage(getFile1 as File)
            val file2 = reduceFileImage(getFile2 as File)
            val file3 = reduceFileImage(getFile3 as File)
            val usia = binding.edUsia.text.toString().toRequestBody("text/plain".toMediaType())
            var gender: RequestBody? = null
            if(binding.radioPria.isChecked){
                gender = "laki-laki".toRequestBody("text/plain".toMediaType())
            } else {
                gender = "perempuan".toRequestBody("text/plain".toMediaType())
            }
            val profesi = binding.edPekerjaan.text.toString().toRequestBody("text/plain".toMediaType())
            val alamatTinggal = binding.edAlamatTinggal.text.toString().toRequestBody("text/plain".toMediaType())
            val alamatKtp = binding.edAlamatKtp.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile1 = file1.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val requestImageFile2 = file2.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val requestImageFile3 = file3.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart1: MultipartBody.Part = MultipartBody.Part.createFormData(
                "foto_diri",
                file1.name,
                requestImageFile1
            )
            val imageMultipart2: MultipartBody.Part = MultipartBody.Part.createFormData(
                "foto_ktp",
                file2.name,
                requestImageFile2
            )
            val imageMultipart3: MultipartBody.Part = MultipartBody.Part.createFormData(
                "foto_selfie",
                file3.name,
                requestImageFile3
            )
            viewModel.postProfile(this, token, imageMultipart1, imageMultipart2, imageMultipart3, usia, gender, alamatTinggal, alamatKtp, profesi)
            viewModel.isSuccessful.observe(this){
                if(it == "success"){
                    val dialog = Dialog(this)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(true)
                    dialog.setContentView(R.layout.register_pop_up_success)
                    val tvSuccessful: TextView = dialog.findViewById(R.id.tv_successful)
                    val btnLogin: Button = dialog.findViewById(R.id.btn_login)
                    tvSuccessful.text = "Data berhasil diperbarui"
                    btnLogin.visibility = View.GONE
                    dialog.show()
                    dialog.setOnDismissListener {
                        recreate()
                    }
                } else if (it == "failed"){
                    val dialog = Dialog(this)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.setCancelable(true)
                    dialog.setContentView(R.layout.register_pop_up_failed)
                    val retryBtn: Button = dialog.findViewById(R.id.btn_retry)
                    val tvFailed: TextView = dialog.findViewById(R.id.tv_failed)
                    val tvError: TextView = dialog.findViewById(R.id.tv_error_message)
                    tvFailed.text = "Data gagal diperbarui"
                    tvError.text = "Terdapat error ketika memperbarui data"
                    retryBtn.setOnClickListener { dialog.dismiss() }
                    dialog.show()
                }
            }
            viewModel.isLoading.observe(this){
                showLoading(it)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarProfile.visibility = View.VISIBLE
        } else {
            binding.progressBarProfile.visibility = View.GONE
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}