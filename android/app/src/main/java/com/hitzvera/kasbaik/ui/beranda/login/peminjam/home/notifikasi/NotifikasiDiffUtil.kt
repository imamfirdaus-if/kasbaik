package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.notifikasi

import androidx.recyclerview.widget.DiffUtil
import com.hitzvera.kasbaik.response.MessageItem
import com.hitzvera.kasbaik.response.PaymentItem

class NotifikasiDiffUtil(
    private val oldList: List<MessageItem>,
    private val newList: List<MessageItem>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].idMessage == newList[newItemPosition].idMessage

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}