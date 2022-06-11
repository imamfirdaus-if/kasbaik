package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hitzvera.kasbaik.R
import com.hitzvera.kasbaik.databinding.ActivityUploadBuktiBayarBinding
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.LoginAsPeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.HomePeminjamActivity
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history.HistoryBuktiBayarActivity
import com.hitzvera.kasbaik.utils.reduceFileImage
import com.hitzvera.kasbaik.utils.rotateBitmap
import com.hitzvera.kasbaik.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UploadBuktiBayarActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUploadBuktiBayarBinding
    private lateinit var token: String
    private lateinit var viewModel: UploadBuktiBayarViewModel
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBuktiBayarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(HomePeminjamActivity.TOKEN).toString()
        viewModel = ViewModelProvider(this)[UploadBuktiBayarViewModel::class.java]
        binding.btnCamera.setOnClickListener(this)
        binding.btnGaleri.setOnClickListener(this)
        binding.btnCancel.setOnClickListener(this)
        binding.btnSend.setOnClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.icon_history -> {
                Intent(this, HistoryBuktiBayarActivity::class.java).also {
                    it.putExtra(TOKEN, token)
                    startActivity(it)
                }
                true
            }
            else -> false
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this)

            getFile = myFile

            binding.ivPreview.setImageURI(selectedImg)
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }


    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            binding.ivPreview.setImageBitmap(result)
        }
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

   private fun validateForm(): Boolean {
       return if((getFile != null && binding.edNominal.text.isNotBlank() && binding.edPesan.text.isNotBlank())){
           true
       } else {
           Toast.makeText(this, "Fill all the form", Toast.LENGTH_SHORT).show()
           false
       }
   }


    override fun onClick(view: View) {
        when(view.id){
            R.id.btn_camera -> {
                startCameraX()
            }
            R.id.btn_galeri -> {
                startGallery()
            }
            R.id.btn_send -> {
                if(validateForm()){
                    val file = reduceFileImage(getFile as File)
                    val nominal = binding.edNominal.text.toString().toRequestBody("text/plain".toMediaType())
                    val message = binding.edPesan.text.toString().toRequestBody("text/plain".toMediaType())
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "file",
                        file.name,
                        requestImageFile
                    )
                    viewModel.postBuktiBayar(token, imageMultipart, nominal, message, this)
                    viewModel.isLoading.observe(this){
                        showLoading(it)
                    }
                    viewModel.isSuccessful.observe(this){
                        showCustomDialog(it)
                    }
                }
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
            val btnLogin: Button = dialog.findViewById(R.id.btn_login)
            val tvMessage: TextView = dialog.findViewById(R.id.tv_successful)
            tvMessage.text = "Bukti berhasil dikirim"
            btnLogin.visibility = View.GONE
            dialog.setOnDismissListener { finish() }
            dialog.show()
        } else if(state == "failed") {
            val dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.register_pop_up_failed)
            viewModel.errorMessage.observe(this){
                val tvErrorMessage: TextView = dialog.findViewById(R.id.tv_error_message)
                tvErrorMessage.text = it
            }
            val retryBtn: Button = dialog.findViewById(R.id.btn_retry)
            retryBtn.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    private fun showLoading(isLoading: Boolean){
        if(isLoading) {
            binding.progressBarUploadBukti.visibility = View.VISIBLE
        } else {
            binding.progressBarUploadBukti.visibility = View.GONE
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val TOKEN = "token"
        const val LINKBUKTI = "linkbukti"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}
