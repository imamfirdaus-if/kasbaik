package com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.mitra

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hitzvera.kasbaik.databinding.RvMitraAdminBinding
import com.hitzvera.kasbaik.response.GetListMitraResponseItem
import com.hitzvera.kasbaik.ui.beranda.login.superadmin.home.mitra.profile.ProfileMitraAdminActivity

class ListMitraAdapter(private val onClickListener: ListMitraAdapter.OnClickListener, private val context: Context, private val token:String): RecyclerView.Adapter<ListMitraAdapter.ViewHolder>() {
    private var oldBorrowerItem = emptyList<GetListMitraResponseItem>()

    inner class ViewHolder(private val binding: RvMitraAdminBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(list: GetListMitraResponseItem){
            binding.apply {
                val id = list.idUser
                tvMitraName.text = list.username
                phone.text = list.phone
//                phone.setOnClickListener {
//                    Intent(context, ProfileMitraAdminActivity::class.java).also {
//                        it.putExtra("TOKEN", token)
//                        it.putExtra("IDUSER", id)
//                        context.startActivity(it)
//                    }
//                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMitraAdapter.ViewHolder =
        ViewHolder(RvMitraAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ListMitraAdapter.ViewHolder, position: Int) {
        holder.bind(oldBorrowerItem[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(oldBorrowerItem[position])
        }
    }

    override fun getItemCount(): Int = oldBorrowerItem.size

    fun setData(newBorrowerList: List<GetListMitraResponseItem>){
        val diffUtil = MitraAdminDiffUtil(oldBorrowerItem, newBorrowerList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldBorrowerItem = newBorrowerList
        diffResult.dispatchUpdatesTo(this)
    }

    class OnClickListener(val clickListener: (getListMitraResponseItem: GetListMitraResponseItem) -> Unit){
        fun onClick(getListMitraResponseItem: GetListMitraResponseItem) = clickListener(getListMitraResponseItem)
    }

}

class MitraAdminDiffUtil(
    private val oldList: List<GetListMitraResponseItem>,
    private val newList: List<GetListMitraResponseItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idUser == newList[newItemPosition].idUser

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}