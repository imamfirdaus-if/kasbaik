package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemPaymentBinding
import com.hitzvera.kasbaik.response.PaymentItem

class HistoryAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var oldBorrowerItem = emptyList<PaymentItem>()

    inner class ViewHolder(private val binding: RvItemPaymentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listBorrower: PaymentItem){
            binding.tanggalPayment.text = listBorrower.createdAt
            binding.status.text = listBorrower.paymentMethod
            binding.jumlahPayment.text = listBorrower.amountPayment.toString()
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

    fun setData(newBorrowerList: List<PaymentItem>){
        val diffUtil = PaymentDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: PaymentItem) -> Unit){
        fun onClick(getUpdateStatusResponseItem: PaymentItem) = clickListener(getUpdateStatusResponseItem)
    }

}