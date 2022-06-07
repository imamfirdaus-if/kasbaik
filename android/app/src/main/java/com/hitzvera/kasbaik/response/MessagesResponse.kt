package com.hitzvera.kasbaik.response

import com.google.gson.annotations.SerializedName

data class MessagesResponse(
    @field:SerializedName("message")
    val message: List<MessageItem>
)
