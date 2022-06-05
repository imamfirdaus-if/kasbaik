package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.riwayat

import androidx.recyclerview.widget.DiffUtil
import com.hitzvera.kasbaik.response.Borrower

class RiwayatDiffUtil(
    private val oldList: List<Borrower>,
    private val newList: List<Borrower>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idBorrower == newList[newItemPosition].idBorrower

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}