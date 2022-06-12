package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hitzvera.kasbaik.api.ApiConfig
import com.hitzvera.kasbaik.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StatusViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private var _listBorrowing = MutableLiveData<List<Borrower>>()
    val listBorrowing: LiveData<List<Borrower>> = _listBorrowing

    private var _listPayment = MutableLiveData<List<PaymentItem>>()
    val listPayment: LiveData<List<PaymentItem>> = _listPayment

    fun getListBorrowing(token: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().getRequestBorrowing("jwt=$token")
            .enqueue(object: Callback<List<Borrower>>{
                override fun onResponse(
                    call: Call<List<Borrower>>,
                    response: Response<List<Borrower>>
                ) {
                    _isLoading.value = false
                   if(response.isSuccessful){
                       _listBorrowing.postValue(response.body())
                   } else {
                       Toast.makeText(context, "Failed fetch data", Toast.LENGTH_LONG).show()
                   }
                }

                override fun onFailure(call: Call<List<Borrower>>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "Failed fetch data", Toast.LENGTH_LONG).show()
                }

            })
    }


    fun getListPayment(token: String, context: Context, id: String){
        _isLoading.value = true
        ApiConfig.getApiService().getPaymentFromUser("jwt=$token", id)
            .enqueue(object : Callback<GetPaymentResponseUser>{
                override fun onResponse(
                    call: Call<GetPaymentResponseUser>,
                    response: Response<GetPaymentResponseUser>
                ) {
                    if (response.isSuccessful){
                        _isLoading.value = false
                        _listPayment.postValue(response.body()?.payment)
                        Log.e("CEK", response.body().toString())
                    } else {
                        _isLoading.value = false
                        Toast.makeText(context, "failed to fetch data", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<GetPaymentResponseUser>, t: Throwable) {
                    _isLoading.value = false
                    Toast.makeText(context, "failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun deleteRequest(token: String, idBorrower: String, context: Context){
        _isLoading.value = true
        ApiConfig.getApiService().deleteRequestBorrowing("jwt=$token", idBorrower)
            .enqueue(object: Callback<DeleteRequestBorrowingResponse>{
                override fun onResponse(
                    call: Call<DeleteRequestBorrowingResponse>,
                    response: Response<DeleteRequestBorrowingResponse>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful){
                        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_LONG).show()
                        _isSuccessful.value = true
                    } else {
                        Toast.makeText(context, "Data Gagal dihapus", Toast.LENGTH_LONG).show()
                        _isSuccessful.value = false
                    }

                }

                override fun onFailure(call: Call<DeleteRequestBorrowingResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isSuccessful.value = false
                    Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_LONG).show()
                }

            })
    }

}