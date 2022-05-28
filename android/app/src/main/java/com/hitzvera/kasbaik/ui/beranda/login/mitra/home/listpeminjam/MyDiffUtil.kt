package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.listpeminjam

import androidx.recyclerview.widget.DiffUtil
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.GetUpdateStatusResponseItem

class MyDiffUtil(
    private val oldList: List<GetUpdateStatusResponseItem>,
    private val newList: List<GetUpdateStatusResponseItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idBorrower == newList[newItemPosition].idBorrower

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}