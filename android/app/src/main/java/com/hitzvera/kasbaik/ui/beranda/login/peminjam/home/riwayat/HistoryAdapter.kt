package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemHistoryPinjamanBinding
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam.MyDiffUtil
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat.detailriwayat.DetailHistoryActivity

class HistoryAdapter(private val onClickListener: OnClickListener, private val context: Context, private val token: String): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var oldBorrowerItem = emptyList<Borrower>()

    inner class ViewHolder(private val binding: RvItemHistoryPinjamanBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listBorrower: Borrower){
            var count = listBorrower.pinjamanKe
            val id = listBorrower.idBorrower
            val amount = listBorrower.loanAmount.toString()
            binding.loanAmount.text = "Rp. $amount"
            binding.loanCount.text = "$count ."
            binding.status.setOnClickListener{
                Intent(context, DetailHistoryActivity::class.java).also {
                    it.putExtra(DetailHistoryActivity.TOKEN, token)
                    it.putExtra(DetailHistoryActivity.ID, id)
                    it.putExtra(DetailHistoryActivity.LOANCOUNT, listBorrower.pinjamanKe)
                    context.startActivity(it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemHistoryPinjamanBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<Borrower>){
        val diffUtil = RiwayatDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: Borrower) -> Unit){
        fun onClick(getUpdateStatusResponseItem: Borrower) = clickListener(getUpdateStatusResponseItem)
    }

}