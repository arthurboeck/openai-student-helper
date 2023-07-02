package com.openai.student.helper.controller

import com.openai.student.helper.BaseControllerTest
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import com.openai.student.helper.service.questionsandanswers.IQuestionsAndAnswersService
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

@WebMvcTest(controllers = [QuestionsAndAnswersController::class])
class QuestionsAndAnswersControllerTest : BaseControllerTest() {

    private val QUESTION_ANSWER_TEXT_ROUTE = "/v1/open-ai/student-helper/question-answer/text"
    private val QUESTION_ANSWER_JSON_ROUTE = "/v1/open-ai/student-helper/question-answer/json"
    
    @MockBean
    private lateinit var iTopicQuestionsService: IQuestionsAndAnswersService

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `Must Returns Bad Request on Questions TEXT when topic is empty`(topic: String) {
        validateBadRequest(QUESTION_ANSWER_TEXT_ROUTE, topic)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(CONSTRAINT_VIOLATION_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(TOPIC_REQUIRED_MSG)))
    }

    @ParameterizedTest
    @ValueSource(strings = ["", " "])
    fun `Must Returns Bad Request on Questions JSON when topic is empty`(topic: String) {
        validateBadRequest(QUESTION_ANSWER_JSON_ROUTE, topic)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(CONSTRAINT_VIOLATION_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(TOPIC_REQUIRED_MSG)))
    }

    @Test
    fun `Must Returns Sucess on Questions TEXT when topic is sent`(){
        validateSuccessRequest(QUESTION_ANSWER_TEXT_ROUTE)
            .andExpect(status().isOk)
            .andExpect(content().string("Bananas são frutas"))
    }

    @Test
    fun `Must Returns Sucess on Questions JSON when topic is sent`(){
        validateSuccessRequest(QUESTION_ANSWER_JSON_ROUTE)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message", `is`("Bananas são frutas")))
    }

    @Test
    fun `Must Returns Unauthorized on Questions TEXT when Open AI Key is Invalid`(){
        validateUnauthorizedRequest(QUESTION_ANSWER_TEXT_ROUTE)
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_INVALID_KEY_MSG)))
    }
    @Test
    fun `Must Returns Unauthorized on Questions JSON when Open AI Key is Invalid`(){
        validateUnauthorizedRequest(QUESTION_ANSWER_JSON_ROUTE)
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
        doReturn("Bananas são frutas").`when`(iTopicQuestionsService).getQuestionsAndAnswers("bananas")

        return mvc.perform(
            get(route)
                .queryParam("topic", "bananas")
        )
    }
    private fun validateUnauthorizedRequest(route: String): ResultActions {
        doThrow(UnauthorizedException("User Unauthorized - Invalid Open AI key"))
            .`when`(iTopicQuestionsService).getQuestionsAndAnswers("bananas")

        return mvc.perform(
            get(route)
                .queryParam("topic", "bananas")
        )
    }
}
