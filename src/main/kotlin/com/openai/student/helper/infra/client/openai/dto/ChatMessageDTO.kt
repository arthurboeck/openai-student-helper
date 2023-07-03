package com.openai.student.helper.infra.client.openai.dto

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatRole

@OptIn(BetaOpenAI::class)
data class ChatMessageDTO(
    val role: ChatRole,
    val content: String,
    val name: String?
)
