package com.hitzvera.kasbaik.response

data class PaymentResponse(
    val tablePayment: List<tablePaymentResponse>
)

data class tablePaymentResponse(
    val id_payment: String,
    val id_mitra: String,
    val payment_method: String,
    val amount_payment: String,
    val id_borrower: String,
    val createdAt: String
)
