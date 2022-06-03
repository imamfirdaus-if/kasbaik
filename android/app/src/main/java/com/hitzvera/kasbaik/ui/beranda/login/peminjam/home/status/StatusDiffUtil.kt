package com.hitzvera.kasbaik.ui.beranda.login.peminjam.home.status

import androidx.recyclerview.widget.DiffUtil
import com.hitzvera.kasbaik.response.TablePaymentResponse

class StatusDiffUtil(
    private val oldList: List<TablePaymentResponse>,
    private val newList: List<TablePaymentResponse>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id_payment == newList[newItemPosition].id_payment

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}
