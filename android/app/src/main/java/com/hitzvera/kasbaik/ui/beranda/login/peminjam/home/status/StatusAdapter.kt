package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.ItemListPeminjamBinding
import com.hitzvera.kasbaik.databinding.RvItemPaymentBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.TablePaymentResponse
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.HistoryAdapter
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.PaymentDiffUtil

class StatusAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<StatusAdapter.ViewHolder>() {

    private var oldBorrowerItem = emptyList<TablePaymentResponse>()

    inner class ViewHolder(private val binding: RvItemPaymentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listBorrower: TablePaymentResponse){
            binding.tanggalPayment.text = listBorrower.createdAt
            binding.status.text = listBorrower.payment_method
            binding.jumlahPayment.text = listBorrower.amount_payment
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

    fun setData(newBorrowerList: List<TablePaymentResponse>){
        val diffUtil = PaymentDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: TablePaymentResponse) -> Unit){
        fun onClick(getUpdateStatusResponseItem: TablePaymentResponse) = clickListener(getUpdateStatusResponseItem)
    }

}
