package com.hitzvera.kasbaik.response

data class SummaryMitraResponse(
    val pending: Int,
    val accepted: Int,
    val borrower: Int,
    val totalPayment: Int,
)
