package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.editprofile

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityEditProfileBinding
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.HomeMitraActivity.Companion.TOKEN
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.profile.ProfileActivity
import com.hitzvera.kasbaik.utils.reduceFileImage
import com.hitzvera.kasbaik.utils.rotateBitmap
import com.hitzvera.kasbaik.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditProfileActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var token: String
    private lateinit var viewModel: EditProfileViewModel
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN).toString()
        viewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]


        bindingData()

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

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@EditProfileActivity)

            getFile = myFile

            binding.ivAvatar.setImageURI(selectedImg)
        }
    }

    private fun validateForm(): Boolean{
        return (binding.tvNama.text.isNotBlank()
                && binding.tvNomor.text.isNotBlank()
                && binding.tvLocation.text.isNotBlank()
                && getFile != null)
    }

    private fun bindingData(){
        val name = intent.getStringExtra(MitraProfileActivity.NAMA)
        val nomor = intent.getStringExtra(MitraProfileActivity.NOMOR)
        val location = intent.getStringExtra(MitraProfileActivity.LOCATION)
        val photo = intent.getStringExtra(MitraProfileActivity.PHOTO)
        Glide.with(this).load(photo ?: "").into(binding.ivAvatar)
        binding.tvNama.setText(name)
        binding.tvNomor.setText(nomor)
        binding.tvLocation.setText(location)
        binding.btnSave.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.tvChangePhoto.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_save -> {
                if(validateForm()){
                    val file = reduceFileImage(getFile as File)
                    val mitraName = binding.tvNama.text.toString().toRequestBody("text/plain".toMediaType())
                    val locationMitra = binding.tvLocation.text.toString().toRequestBody("text/plain".toMediaType())
                    val phone = binding.tvNomor.text.toString().toRequestBody("text/plain".toMediaType())
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "foto_profile",
                        file.name,
                        requestImageFile
                    )
                    viewModel.postEditMitraProfile(token, imageMultipart, mitraName, locationMitra, phone, this)
                    viewModel.isSuccessful.observe(this){
                        showCustomDialog(it)
                    }
                    viewModel.isLoading.observe(this){
                        showLoading(it)
                    }
                } else {
                    Toast.makeText(this, "Fillout all the form frist please", Toast.LENGTH_LONG).show()
                }
            }
            R.id.tv_change_photo -> {
                startGallery()
                Log.e("test", "masuk")
            }
            R.id.btn_cancel -> {
                finish()
            }
        }
    }

    private fun showCustomDialog(state: String){
        if(state == "success"){
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
        } else if(state == "failed") {
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

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarEditProfileMitra.visibility = View.VISIBLE
        } else {
            binding.progressBarEditProfileMitra.visibility = View.GONE
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}