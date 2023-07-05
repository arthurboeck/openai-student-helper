package com.openai.student.helper.infra.client.openai

import com.openai.student.helper.infra.client.openai.dto.ChatCompletionRequestDTO
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionResponseDTO
import com.openai.student.helper.infra.client.openai.dto.ChatMessageDTO
import com.openai.student.helper.infra.exceptions.NotFoundException
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import feign.FeignException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Service
import javax.naming.ServiceUnavailableException

@Service
class OpenAIService(
        @Value("\${service.open-ai.key}")
        private val openAiKey: String? = null,
        private val iOpenAIClient: IOpenAIClient
) : IOpenAIService {

    private val modelId: String = "gpt-3.5-turbo"

    override fun integrateChatGpt(context: String, question: String): String {
        val chatCompletionRequest = generateChatCompletionRequest(context, question)

        try {
            val response = integrateOpenAIClient(chatCompletionRequest)
            return response.choices.first().message?.content ?: throw NotFoundException("No message")

        } catch (e: FeignException.Unauthorized) {
            throw UnauthorizedException("User Unauthorized - Invalid Open AI key, $e")
        }
    }

    private fun integrateOpenAIClient(chatCompletionRequest: ChatCompletionRequestDTO): ChatCompletionResponseDTO {
        return iOpenAIClient.chatCompletion(generateAuth(), chatCompletionRequest)
    }

    private fun fallback() {
        throw ServiceUnavailableException("Service unavailable for a while.")
    }

    private fun generateChatCompletionRequest(context: String, question: String): ChatCompletionRequestDTO {
        return ChatCompletionRequestDTO(
                model = modelId,
                messages = listOf(
                        ChatMessageDTO(role = "system", content = context),
                        ChatMessageDTO(role = "user", content = question)
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