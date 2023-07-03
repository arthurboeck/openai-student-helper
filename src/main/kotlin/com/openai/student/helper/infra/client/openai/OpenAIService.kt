package com.openai.student.helper.infra.client.openai

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.exception.AuthenticationException
import com.aallam.openai.api.model.ModelId
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class OpenAIService(
    @Value("\${service.open-ai.key}")
    val openAiKey: String? = null,
    private val openAiFeignClient: IOpenAIClient
) : IOpenAIService {

    private val modelId: String = "gpt-3.5-turbo"

    @OptIn(BetaOpenAI::class)
    override fun integrateChatGpt(context: String, question: String): String {
        val chatCompletionRequest = generateChatCompletionRequest(context, question)

        try {
            val response = openAiFeignClient.chatCompletion(generateAuth(), chatCompletionRequest)
            return response.choices.first().message?.content ?: throw ResponseStatusException(NOT_FOUND,"No message")

        } catch (e: AuthenticationException) {
            throw UnauthorizedException("User Unauthorized - Invalid Open AI key, $e")
        }
    }

    @OptIn(BetaOpenAI::class)
    private fun generateChatCompletionRequest(context: String, question: String): ChatCompletionRequest {
        return ChatCompletionRequest(
            model = ModelId(modelId), messages = listOf(
                ChatMessage(role = ChatRole.System, content = context),
                ChatMessage(role = ChatRole.User, content = question)
            )
        )
    }

    private fun generateAuth(): String {
        if (openAiKey == null) {
            throw UnauthorizedException("User Unauthorized - Open AI Key Not Set")
        }

        return "Bearer $openAiKey"
    }
}