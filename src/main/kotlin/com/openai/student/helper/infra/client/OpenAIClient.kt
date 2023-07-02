package com.openai.student.helper.infra.client

import com.aallam.openai.api.BetaOpenAI
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.exception.AuthenticationException
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class OpenAIClient(
    @Value("\${service.open-ai.key}")
    val openAiKey: String? = null
) : IOpenAIClient {

    private val modelId: String = "gpt-3.5-turbo"
    private lateinit var openAIService: OpenAI

    init {
        openAiKey?.let {
            openAIService = OpenAI(token = it)
        } ?: throw UnauthorizedException("User Unauthorized - Open AI Key Not Set")
    }

    @OptIn(BetaOpenAI::class)
    override fun integrateChatGpt(context: String, question: String): String {
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId(modelId), messages = listOf(
                ChatMessage(role = ChatRole.System, content = context),
                ChatMessage(role = ChatRole.User, content = question)
            )
        )

        return runBlocking {
            try {
                val response = openAIService.chatCompletion(chatCompletionRequest)
                 response.choices.first().message?.content ?: throw ResponseStatusException(NOT_FOUND,"No message")

            } catch (e: AuthenticationException) {
                throw UnauthorizedException("User Unauthorized - Invalid Open AI key, $e")
            }
        }
    }
}