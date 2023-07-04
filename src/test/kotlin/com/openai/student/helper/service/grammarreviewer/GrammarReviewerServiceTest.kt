package com.openai.student.helper.service.grammarreviewer

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
class GrammarReviewerServiceTest : BaseUnitTest() {

    @MockBean
    lateinit var openAIClient: IOpenAIService
    lateinit var service: GrammarReviewerService

    @BeforeEach
    fun setUp() {
        service = GrammarReviewerService(openAIClient)
    }

    @Test
    fun `Must return suggested content for grammar revision`() {
        val fileText = "This is a sample text"
        val expectedResponse = "Reviewed text"

        val context = "Você é um revisor de textos."
        val question = "Revise o texto a seguir e sugira melhorias gramaticais, retornando apenas o texto corrido: \"$fileText\""

        `when`(openAIClient.integrateChatGpt(context, question)).thenReturn(expectedResponse)

        val result = service.getGrammarReviewerService(fileText)
        val expectedOutput = "Este é o texto ajustado, contendo as minhas sugestões de melhoria: \n\n $expectedResponse"

        assertThat(result, `is`(expectedOutput))
    }
}