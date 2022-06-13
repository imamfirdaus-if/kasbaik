package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran.history

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemPaymentBinding
import com.hitzvera.kasbaik.response.TablePaymentItem

class HistoryPaymentAdminAdapter(private val onClickListener: HistoryPaymentAdminAdapter.OnClickListener, private val token: String, private val context: Context): RecyclerView.Adapter<HistoryPaymentAdminAdapter.ViewHolder>() {
    private var oldBorrowerItem = emptyList<TablePaymentItem>()

    inner class ViewHolder(private val binding: RvItemPaymentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(list: TablePaymentItem){
            binding.apply {
                tanggalPayment.text = list.createdAt
                status.text = list.paymentMethod
                jumlahPayment.text = "Rp. ${list.amountPayment}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPaymentAdminAdapter.ViewHolder =
        ViewHolder(RvItemPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: HistoryPaymentAdminAdapter.ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<TablePaymentItem>){
        val diffUtil = HistoryPaymentAdminDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getListPeminjamResponseItem: TablePaymentItem) -> Unit){
        fun onClick(getListPeminjamResponseItem: TablePaymentItem) = clickListener(getListPeminjamResponseItem)
    }

}

class HistoryPaymentAdminDiffUtil(
    private val oldList: List<TablePaymentItem>,
    private val newList: List<TablePaymentItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idPayment == newList[newItemPosition].idPayment

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}