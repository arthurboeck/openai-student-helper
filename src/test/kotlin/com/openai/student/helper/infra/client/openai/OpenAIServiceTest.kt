package com.openai.student.helper.infra.client.openai

import com.openai.student.helper.BaseUnitTest
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionChoiceDTO
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionRequestDTO
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionResponseDTO
import com.openai.student.helper.infra.client.openai.dto.ChatMessageDTO
import com.openai.student.helper.infra.exceptions.NotFoundException
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import feign.FeignException
import feign.Request.HttpMethod.GET
import feign.Request.create
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean
import java.nio.charset.Charset.defaultCharset


@ExtendWith(MockitoExtension::class)
class OpenAIServiceTest : BaseUnitTest() {

    @MockBean
    lateinit var openAIClient: IOpenAIClient
    private lateinit var openAIService: OpenAIService

    private val token = "YOUR_OPENAI_KEY"
    private val bearerToken = "Bearer $token"
    private val context = "System context"
    private val question = "User question"

    @BeforeEach
    fun setup() {
        openAIService = OpenAIService(openAiKey = token, iOpenAIClient = openAIClient)
    }

    @Test
    fun `Must integrate successfully with OpenAI`() {
        val expectedResponse = "Response"
        val chatCompletionRequest = getChatCompletitionRequest()
        val completionResponse = ChatCompletionResponseDTO(
            listOf(
                ChatCompletionChoiceDTO(
                    ChatMessageDTO("user", expectedResponse)
                )
            )
        )

        doReturn(completionResponse).`when`(openAIClient)
            .chatCompletion(token = bearerToken, request = chatCompletionRequest)

        val response = openAIService.integrateChatGpt(context, question)
        assertThat(expectedResponse).isEqualTo(response)
    }

    @Test
    fun `Must throw UnauthorizedException when get FeignException-Unauthorized for invalid openai key`() {
        val chatCompletionRequest = getChatCompletitionRequest()
        val feignExceptionUnauthorized =
            FeignException.Unauthorized(
                "Unauthorized",
                create(GET, "", emptyMap(), "Unauthorized".toByteArray(), defaultCharset()),
                "Unauthorized".toByteArray(),
                emptyMap(),
            )

        doThrow(feignExceptionUnauthorized).`when`(openAIClient)
            .chatCompletion(token = bearerToken, request = chatCompletionRequest)

        val exception = assertThrows<UnauthorizedException> {
            openAIService.integrateChatGpt(context, question)
        }

        assertThat("User Unauthorized - Invalid Open AI key, feign.FeignException\$Unauthorized: Unauthorized")
            .isEqualTo(exception.message)
    }

    @Test
    fun `Must throw UnauthorizedException when openai key not set`() {
        val openAIServiceLocal = OpenAIService(openAiKey = null, iOpenAIClient = openAIClient)

        val exception = assertThrows<UnauthorizedException> {
            openAIServiceLocal.integrateChatGpt(context, question)
        }

        assertThat("User Unauthorized - Open AI Key Not Set")
            .isEqualTo(exception.message)
    }

    @Test
    fun `Must throw NotFoundException when get an empty return from OpenAI`() {
        val chatCompletionRequest = getChatCompletitionRequest()
        val completionResponse = ChatCompletionResponseDTO(
            listOf(
                ChatCompletionChoiceDTO(
                    ChatMessageDTO("user", null)
                )
            )
        )

        doReturn(completionResponse).`when`(openAIClient)
            .chatCompletion(token = bearerToken, request = chatCompletionRequest)

        val exception = assertThrows<NotFoundException> {
            openAIService.integrateChatGpt(context, question)
        }

        assertThat("No message").isEqualTo(exception.message)
    }

    private fun getChatCompletitionRequest(): ChatCompletionRequestDTO {
        return ChatCompletionRequestDTO(
            model = "gpt-3.5-turbo",
            messages = listOf(
                ChatMessageDTO(role = "system", content = context),
                ChatMessageDTO(role = "user", content = question)
            )
        )
    }
}
