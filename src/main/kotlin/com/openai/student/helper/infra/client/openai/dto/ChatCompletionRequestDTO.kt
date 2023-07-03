package com.openai.student.helper.infra.client.openai.dto

data class ChatCompletionRequestDTO(
    val model: String,
    val messages: List<ChatMessageDTO>
)
