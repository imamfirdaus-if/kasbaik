package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.ItemListPeminjamBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.GetUpdateStatusResponseItem

class ListPeminjamAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<ListPeminjamAdapter.ViewHolder>() {

    private var oldBorrowerItem = emptyList<GetUpdateStatusResponseItem>()

    inner class ViewHolder(private val binding: ItemListPeminjamBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listBorrower: GetUpdateStatusResponseItem){
            binding.jumlahPinjaman.text = listBorrower.loanAmount.toString()
            binding.namaPeminjam.text = listBorrower.namaLengkap
            binding.status.text = listBorrower.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemListPeminjamBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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