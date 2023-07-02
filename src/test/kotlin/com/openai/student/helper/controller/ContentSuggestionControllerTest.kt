package com.openai.student.helper.controller

import com.openai.student.helper.BaseControllerTest
import com.openai.student.helper.infra.client.IOpenAIClient
import com.openai.student.helper.service.contentsuggestion.IContentSuggestionService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(controllers = [ContentSuggestionController::class])
class ContentSuggestionControllerTest : BaseControllerTest() {

    @MockBean
    private lateinit var iContentSuggestion: IContentSuggestionService

    @MockBean
    private lateinit var iOpenAIClient: IOpenAIClient

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `Must Resturns Bad Request on Content Suggestion TEXT when topic is empty`(topic: String) {
        mvc.perform(
            get("/v1/open-ai/student-helper/content-suggestion/text")
                .queryParam("topic", topic)
        )
            .andExpect { status().isBadRequest }
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `Must Resturns Bad Request on Content Suggestion JSON when topic is empty`(topic: String) {
        mvc.perform(
            get("/v1/open-ai/student-helper/content-suggestion/json")
                .queryParam("topic", topic)
        )
            .andExpect { status().isBadRequest }
    }

    @Test
    fun `Must Resturns Sucess on Content Suggestion TEXT when topic is sent`(){
//        doReturn("Bananas são frutas").`when`(iOpenAIClient).integrateChatGpt(anyString(), anyString())
        doReturn("Bananas são frutas").`when`(iContentSuggestion).getContentSuggestion("bananas")

        mvc.perform(
            get("/v1/open-ai/student-helper/content-suggestion/text")
                .queryParam("topic", "bananas")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Bananass são frutas"))
    }
}
