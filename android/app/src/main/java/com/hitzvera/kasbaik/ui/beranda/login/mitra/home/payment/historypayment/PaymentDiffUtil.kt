package com.hitzvera.kasbaik.ui.beranda.login.mitra.home.payment.historypayment

import androidx.recyclerview.widget.DiffUtil
import com.hitzvera.kasbaik.response.Borrower
import com.hitzvera.kasbaik.response.GetUpdateStatusResponseItem
import com.hitzvera.kasbaik.response.tablePaymentResponse

class PaymentDiffUtil(
    private val oldList: List<tablePaymentResponse>,
    private val newList: List<tablePaymentResponse>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id_payment == newList[newItemPosition].id_payment

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}