package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemPeminjamBinding
import com.hitzvera.kasbaik.response.GetUpdateStatusResponseItem
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.MyDiffUtil
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.detailpayment.DetailPaymentActivity
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryActivity

class ListAdapter(private val onClickListener: OnClickListener, private val context: Context, private val token: String): RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    private var oldBorrowerItem = emptyList<GetUpdateStatusResponseItem>()

    inner class ViewHolder(private val binding: RvItemPeminjamBinding): RecyclerView.ViewHolder(binding.root){
        var status: TextView = binding.status
        fun bind(listBorrower: GetUpdateStatusResponseItem){
            binding.jumlahPinjaman.text = listBorrower.loanAmount.toString()
            binding.namaPeminjam.text = listBorrower.namaLengkap
            binding.status.text = listBorrower.status
            binding.btAddPayment.setOnClickListener {
                if (listBorrower.status == "payment") {
                    Intent(context, DetailPaymentActivity::class.java).also {
                        it.putExtra(DetailPaymentActivity.ID_BORROWER, listBorrower.idBorrower)
                        it.putExtra(DetailPaymentActivity.NAMA_PEMINJAM, listBorrower.namaLengkap)
                        it.putExtra(DetailPaymentActivity.STATUS, listBorrower.status)
                        it.putExtra(DetailPaymentActivity.CREDIT_SCORE, listBorrower.creditScore)
                        it.putExtra(DetailPaymentActivity.LOAN_AMOUNT, listBorrower.loanAmount)
                        it.putExtra(DetailPaymentActivity.TOKEN, token)
                        context.startActivity(it)
                    }
                } else {
                    if (listBorrower.status == "done") {
                        Toast.makeText(context, "Pengguna tersebut telah menyelesaikan pembayarannya", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Peminjam belum menerima uang", Toast.LENGTH_LONG).show()
                    }
                }

            }
            binding.btHistoryPayment.setOnClickListener {
                Intent(context, HistoryActivity::class.java).also {
                    it.putExtra(HistoryActivity.ID_BORROWER, listBorrower.idBorrower)
                    it.putExtra(HistoryActivity.NAMA, listBorrower.namaLengkap)
                    it.putExtra(HistoryActivity.TOKEN, token)
                    context.startActivity(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemPeminjamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size



    fun setData(newBorrowerList: List<GetUpdateStatusResponseItem>){
        val diffUtil = MyDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: GetUpdateStatusResponseItem) -> Unit){
        fun onClick(getUpdateStatusResponseItem: GetUpdateStatusResponseItem) = clickListener(getUpdateStatusResponseItem)
    }

}