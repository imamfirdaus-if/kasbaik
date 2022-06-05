package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat.detailriwayat


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemRiwayatPembayaranBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.PaymentItem
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment.PaymentDiffUtil
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.StatusDiffUtil

class DetailHistoryAdapter(private val onClickListener: OnClickListener, private val count: String): RecyclerView.Adapter<DetailHistoryAdapter.ViewHolder>() {

    private var oldBorrowerItem = emptyList<PaymentItem>()

    inner class ViewHolder(private val binding: RvItemRiwayatPembayaranBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(detail: PaymentItem){
            val amount = detail.amountPayment.toString()
            binding.monthCount.text = detail.createdAt
            binding.loanAmount.text = "Rp. $amount"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemRiwayatPembayaranBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<PaymentItem>){
        val diffUtil = StatusDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: PaymentItem) -> Unit){
        fun onClick(getUpdateStatusResponseItem: PaymentItem) = clickListener(getUpdateStatusResponseItem)
    }

}