package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemNotifikasiBinding
import com.hitzvera.kasbaik.databinding.RvItemRiwayatPembayaranBinding
import com.hitzvera.kasbaik.response.MessageItem
import com.hitzvera.kasbaik.response.PaymentItem
import com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status.StatusDiffUtil

class NotifikasiAdapter: RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {
    private var oldMessages = emptyList<MessageItem>()

    inner class ViewHolder(private val binding: RvItemNotifikasiBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(message: MessageItem){
            binding.tvMessage.text = message.message
            if(message.hasRead){
                binding.hasRead.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvItemNotifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldMessages[position])
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(oldBorrowerItem[position])
//        }
    }

    override fun getItemCount(): Int = oldMessages.size

    fun setData(newBorrowerList: List<MessageItem>){
        val diffUtil = NotifikasiDiffUtil(oldMessages, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldMessages = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getUpdateStatusResponseItem: PaymentItem) -> Unit){
        fun onClick(getUpdateStatusResponseItem: PaymentItem) = clickListener(getUpdateStatusResponseItem)
    }

}