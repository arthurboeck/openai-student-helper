package com.openai.student.helper.service.questionsandanswers

import com.openai.student.helper.BaseUnitTest
import com.openai.student.helper.infra.client.IOpenAIClient
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean

@ExtendWith(MockitoExtension::class)
class QuestionsAndAnswersServiceTest : BaseUnitTest() {

    @MockBean
    lateinit var openAIClient: IOpenAIClient
    lateinit var service: QuestionsAndAnswersService

    @BeforeEach
    fun setUp() {
        service = QuestionsAndAnswersService(openAIClient)
    }

    @Test
    fun `Must return questions and answers for suggested content`() {
        val topic = "Math"
        val expectedResponse = "Suggested content"

        val context = "Você é um auxiliar para estudos."
        val question = "Me sugira três perguntas e suas respostas sobre: \"$topic\""

        `when`(openAIClient.integrateChatGpt(context, question)).thenReturn(expectedResponse)

        val result = service.getQuestionsAndAnswers(topic)

        assertThat(expectedResponse, `is`(result))
    }
}