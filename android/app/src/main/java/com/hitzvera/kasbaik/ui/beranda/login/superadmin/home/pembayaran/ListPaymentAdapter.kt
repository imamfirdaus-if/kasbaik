package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemPeminjamAdminBinding
import com.hitzvera.kasbaik.response.ListPaymentAdminResponseItem
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pembayaran.history.HistoryPaymentAdminActivity

class ListPaymentAdapter(private val onClickListener: ListPaymentAdapter.OnClickListener, private val token: String, private val context: Context): RecyclerView.Adapter<ListPaymentAdapter.ViewHolder>() {
    private var oldBorrowerItem = emptyList<ListPaymentAdminResponseItem>()
    var count = 0

    inner class ViewHolder(private val binding: RvItemPeminjamAdminBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(list: ListPaymentAdminResponseItem){
            count +=1
            binding.apply {
                namaPeminjam.text = "${count}."
                status.text = "Rp. ${list.loanAmount}"
                jumlahPinjaman.text = "Rp. ${list.totalPayment}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPaymentAdapter.ViewHolder =
        ViewHolder(RvItemPeminjamAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ListPaymentAdapter.ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<ListPaymentAdminResponseItem>){
        val diffUtil = PeminjamAdminDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getListPeminjamResponseItem: ListPaymentAdminResponseItem) -> Unit){
        fun onClick(getListPeminjamResponseItem: ListPaymentAdminResponseItem) = clickListener(getListPeminjamResponseItem)
    }

}

class PeminjamAdminDiffUtil(
    private val oldList: List<ListPaymentAdminResponseItem>,
    private val newList: List<ListPaymentAdminResponseItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idBorrower == newList[newItemPosition].idBorrower

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}