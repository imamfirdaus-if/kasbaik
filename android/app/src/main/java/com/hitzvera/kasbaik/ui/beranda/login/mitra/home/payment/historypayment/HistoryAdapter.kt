package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.ItemListPeminjamBinding
import com.hitzvera.kasbaik.databinding.RvItemPaymentBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.tablePaymentResponse
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.MyDiffUtil

class HistoryAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var oldBorrowerItem = emptyList<tablePaymentResponse>()

    inner class ViewHolder(private val binding: RvItemPaymentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listBorrower: tablePaymentResponse){
            binding.tanggalPayment.text = listBorrower.createdAt
            binding.status.text = listBorrower.payment_method
            binding.jumlahPayment.text = listBorrower.amount_payment.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<tablePaymentResponse>){
        val diffUtil = PaymentDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: tablePaymentResponse) -> Unit){
        fun onClick(getUpdateStatusResponseItem: tablePaymentResponse) = clickListener(getUpdateStatusResponseItem)
    }

}