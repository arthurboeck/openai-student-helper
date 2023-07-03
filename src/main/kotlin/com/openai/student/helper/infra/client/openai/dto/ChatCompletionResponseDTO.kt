package com.openai.student.helper.infra.client.openai.dto

data class ChatCompletionResponseDTO(
    val choices: List<ChatCompletionChoiceDTO>
)
