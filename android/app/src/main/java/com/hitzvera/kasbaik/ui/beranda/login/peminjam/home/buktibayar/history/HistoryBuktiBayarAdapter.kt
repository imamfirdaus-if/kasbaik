package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.buktibayar.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hitzvera.kasbaik.databinding.RvItemHistoryBuktiBayarBinding
import com.hitzvera.kasbaik.databinding.RvItemNotifikasiBinding
import com.hitzvera.kasbaik.response.MessageItem
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiAdapter
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi.NotifikasiDiffUtil


class HistoryBuktiBayarAdapter(private val onClickListener: OnClickListener): RecyclerView.Adapter<HistoryBuktiBayarAdapter.ViewHolder>() {
    private var oldMessages = emptyList<MessageItem>()

    inner class ViewHolder(private val binding: RvItemHistoryBuktiBayarBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(buktiBayar: MessageItem){
            binding.tvMessage.text = buktiBayar.message
            Glide.with(itemView)
                .load(buktiBayar.linkBukti)
                .into(binding.ivImageBukti)
            binding.tvNominal.text = buktiBayar.nominal.toString()
            val status: String = buktiBayar.isAccepted.toString()
            if(buktiBayar.isAccepted != null){
                binding.tvIsaccepted.text = "Status: $status"
            } else {
                binding.tvIsaccepted.text = "Status has not been reviewed"
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemHistoryBuktiBayarBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldMessages[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldMessages[position])
        }
    }

    override fun getItemCount(): Int = oldMessages.size

    fun setData(newBorrowerList: List<MessageItem>){
        val diffUtil = NotifikasiDiffUtil(oldMessages, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldMessages = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (messageItem: MessageItem) -> Unit){
        fun onClick(messageItem: MessageItem) = clickListener(messageItem)
    }

}