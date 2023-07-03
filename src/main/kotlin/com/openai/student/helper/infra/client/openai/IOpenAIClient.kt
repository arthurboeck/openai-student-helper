package com.openai.student.helper.infra.client.openai

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionResponseDTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "openai-client", url = "\${service.open-ai.url}")
fun interface IOpenAIClient {

    @OptIn(BetaOpenAI::class)
    @PostMapping("/chat/completions")
    fun chatCompletion(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: ChatCompletionRequest
    ): ChatCompletionResponseDTO
}