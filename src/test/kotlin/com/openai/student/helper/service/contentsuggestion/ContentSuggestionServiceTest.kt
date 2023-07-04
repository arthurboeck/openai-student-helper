package com.openai.student.helper.service.contentsuggestion

import com.openai.student.helper.BaseUnitTest
import com.openai.student.helper.infra.client.openai.IOpenAIService
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean

@ExtendWith(MockitoExtension::class)
class ContentSuggestionServiceTest : BaseUnitTest() {

    @MockBean
    lateinit var openAIClient: IOpenAIService
    lateinit var service: ContentSuggestionService

    @BeforeEach
    fun setUp() {
        service = ContentSuggestionService(openAIClient)
    }

    @Test
    fun `Must return suggested content`() {
        val topic = "Math"
        val expectedResponse = "Suggested content"

        val context = "Você é um auxiliar para estudos."
        val question = "Me sugira três pontos chave para estudar sobre: \"$topic\""

        `when`(openAIClient.integrateChatGpt(context, question)).thenReturn(expectedResponse)

        val result = service.getContentSuggestion(topic)

        assertThat(expectedResponse, `is`(result))
    }
}