package com.openai.student.helper.controller

import com.openai.student.helper.BaseControllerTest
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import com.openai.student.helper.service.contentsuggestion.IContentSuggestionService
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [ContentSuggestionController::class])
class ContentSuggestionControllerTest : BaseControllerTest() {

    private val CONTENT_SUGGESTION_TEXT = "/v1/open-ai/student-helper/content-suggestion/text"
    private val CONTENT_SUGGESTION_JSON = "/v1/open-ai/student-helper/content-suggestion/json"
    
    @MockBean
    private lateinit var iContentSuggestion: IContentSuggestionService

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `Must Returns Bad Request on Content Suggestion TEXT when topic is empty`(topic: String) {
        validateBadRequest(CONTENT_SUGGESTION_TEXT, topic)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(CONSTRAINT_VIOLATION_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(TOPIC_REQUIRED_MSG)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `Must Returns Bad Request on Content Suggestion JSON when topic is empty`(topic: String) {
        validateBadRequest(CONTENT_SUGGESTION_JSON, topic)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(CONSTRAINT_VIOLATION_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(TOPIC_REQUIRED_MSG)))
    }

    @Test
    fun `Must Returns Sucess on Content Suggestion TEXT when topic is sent`(){
        validateSuccessRequest(CONTENT_SUGGESTION_TEXT)
            .andExpect(status().isOk)
            .andExpect(content().string("Bananas são frutas"))
    }

    @Test
    fun `Must Returns Sucess on Content Suggestion JSON when topic is sent`(){
        validateSuccessRequest(CONTENT_SUGGESTION_JSON)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message", `is`("Bananas são frutas")))
    }

    @Test
    fun `Must Returns Unauthorized on Content Suggestion TEXT when Open AI Key is Invalid`(){
        validateUnauthorizedRequest(CONTENT_SUGGESTION_TEXT)
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_INVALID_KEY_MSG)))
    }
    @Test
    fun `Must Returns Unauthorized on Content Suggestion JSON when Open AI Key is Invalid`(){
        validateUnauthorizedRequest(CONTENT_SUGGESTION_JSON)
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_INVALID_KEY_MSG)))
    }

    private fun validateBadRequest(route: String,topic: String): ResultActions {
        return mvc.perform(
            get(route)
                .queryParam("topic", topic)
        )
    }
    private fun validateSuccessRequest(route: String): ResultActions {
        doReturn("Bananas são frutas").`when`(iContentSuggestion).getContentSuggestion("bananas")

        return mvc.perform(
            get(route)
                .queryParam("topic", "bananas")
        )
    }
    private fun validateUnauthorizedRequest(route: String): ResultActions {
        doThrow(UnauthorizedException("User Unauthorized - Invalid Open AI key"))
            .`when`(iContentSuggestion).getContentSuggestion("bananas")

        return mvc.perform(
            get(route)
                .queryParam("topic", "bananas")
        )
    }
}
