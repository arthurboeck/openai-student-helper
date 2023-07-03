package com.openai.student.helper.infra.client.openai.dto

data class ChatMessageDTO(
    val role: String,
    val content: String?
)
