package com.openai.student.helper.infra.client.openai

import com.openai.student.helper.infra.client.openai.dto.ChatCompletionRequestDTO
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionResponseDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "openai-client", url = "\${service.open-ai.url}")
fun interface IOpenAIClient {

    @PostMapping("/chat/completions")
    fun chatCompletion(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: ChatCompletionRequestDTO
    ): ChatCompletionResponseDTO
}