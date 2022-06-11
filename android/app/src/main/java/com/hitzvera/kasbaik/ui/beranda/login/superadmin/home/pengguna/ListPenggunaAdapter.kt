package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvItemPeminjamBinding
import com.hitzvera.kasbaik.databinding.RvMitraAdminBinding
import com.hitzvera.kasbaik.response.ListUserAdminResponseItem
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.pengguna.profile.ProfileUserAdminActivity

class ListPenggunaAdapter(private val onClickListener: OnClickListener, private val context: Context, private val token: String): RecyclerView.Adapter<ListPenggunaAdapter.ViewHolder>() {
    private var oldBorrowerItem = emptyList<ListUserAdminResponseItem>()

    inner class ViewHolder(private val binding: RvMitraAdminBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(listBorrower: ListUserAdminResponseItem){
            binding.apply {
                tvMitraName.text = listBorrower.username
                phone.text = "Detail"
                phone.setOnClickListener {
                    Intent(context, ProfileUserAdminActivity::class.java).also {
                        it.putExtra("TOKEN", token)
                        context.startActivity(it)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RvMitraAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size



    fun setData(newBorrowerList: List<ListUserAdminResponseItem>){
        val diffUtil = PenggunaAdminDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (ListUserAdminResponseItem: ListUserAdminResponseItem) -> Unit){
        fun onClick(ListUserAdminResponseItem: ListUserAdminResponseItem) = clickListener(ListUserAdminResponseItem)
    }

}

class PenggunaAdminDiffUtil(
    private val oldList: List<ListUserAdminResponseItem>,
    private val newList: List<ListUserAdminResponseItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idUser == newList[newItemPosition].idUser

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}