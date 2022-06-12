package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.peminjam

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.ItemListPeminjamBinding
import com.hitzvera.kasbaik.response.ListBorrowerPendingResponseItem


class ListPeminjamAdapter(private val onClickListener: ListPeminjamAdapter.OnClickListener): RecyclerView.Adapter<ListPeminjamAdapter.ViewHolder>() {
    private var oldBorrowerItem = emptyList<ListBorrowerPendingResponseItem>()

    inner class ViewHolder(private val binding: ItemListPeminjamBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(list: ListBorrowerPendingResponseItem){
            binding.apply {
                namaPeminjam.text = list.namaLengkap
                status.text = list.status
                jumlahPinjaman.text = list.loanAmount.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPeminjamAdapter.ViewHolder =
        ViewHolder(ItemListPeminjamBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ListPeminjamAdapter.ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<ListBorrowerPendingResponseItem>){
        val diffUtil = PeminjamAdminDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getListPeminjamResponseItem: ListBorrowerPendingResponseItem) -> Unit){
        fun onClick(getListPeminjamResponseItem: ListBorrowerPendingResponseItem) = clickListener(getListPeminjamResponseItem)
    }

}

class PeminjamAdminDiffUtil(
    private val oldList: List<ListBorrowerPendingResponseItem>,
    private val newList: List<ListBorrowerPendingResponseItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idUser == newList[newItemPosition].idUser

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}