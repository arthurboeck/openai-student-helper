package com.openai.student.helper.controller

import com.openai.student.helper.BaseUnitTest
import com.openai.student.helper.infra.exceptions.NotFoundException
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import com.openai.student.helper.service.grammarreviewer.IGrammarReviewerService
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(controllers = [GrammarReviewerController::class])
class GrammarReviewerControllerTest : BaseUnitTest() {

    private val GRAMMAR_REVIEWER_TEXT_ROUTE = "/v1/open-ai/student-helper/grammar-reviewer/text"
    private val GRAMMAR_REVIEWER_JSON_ROUTE = "/v1/open-ai/student-helper/grammar-reviewer/json"

    @MockBean
    private lateinit var iGrammarReviewerService: IGrammarReviewerService

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun `Must Returns Bad Request on Grammar Reviewer TEXT when no file is sent`() {
        validateBadRequest(GRAMMAR_REVIEWER_TEXT_ROUTE)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(REQUEST_PART_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(REQUIRED_PART_NOT_PRESENT_MSG)))
    }

    @Test
    fun `Must Returns Bad Request on Grammar Reviewer JSON when no file is sent`() {
        validateBadRequest(GRAMMAR_REVIEWER_JSON_ROUTE)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(REQUEST_PART_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(REQUIRED_PART_NOT_PRESENT_MSG)))
    }

    @Test
    fun `Must Returns Bad Request on Grammar Reviewer TEXT when sending a non txt file`() {
        validateBadRequestNonTxtFile(GRAMMAR_REVIEWER_TEXT_ROUTE)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(INVALID_FILE_TYPE_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(INVALID_FILE_MSG)))
    }

    @Test
    fun `Must Returns Bad Request on Grammar Reviewer JSON when sending a non txt file`() {
        validateBadRequestNonTxtFile(GRAMMAR_REVIEWER_JSON_ROUTE)
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error", containsString(INVALID_FILE_TYPE_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(INVALID_FILE_MSG)))
    }

    @Test
    fun `Must Returns Sucess on Grammar Reviewer TEXT when sending a txt file`() {
        validateSuccessRequest(GRAMMAR_REVIEWER_TEXT_ROUTE)
            .andExpect(status().isOk)
            .andExpect(content().string("Text content of the file to be uploaded"))
    }

    @Test
    fun `Must Returns Sucess on Grammar Reviewer JSON when sending a txt file`() {
        validateSuccessRequest(GRAMMAR_REVIEWER_JSON_ROUTE)
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.message", `is`("Text content of the file to be uploaded")))
    }

    @Test
    fun `Must Returns Unauthorized on Grammar Reviewer TEXT when Open AI Key is Invalid`() {
        validateUnauthorizedRequest(GRAMMAR_REVIEWER_TEXT_ROUTE)
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_INVALID_KEY_MSG)))
    }

    @Test
    fun `Must Returns Unauthorized on Grammar Reviewer JSON when Open AI Key is Invalid`() {
        validateUnauthorizedRequest(GRAMMAR_REVIEWER_JSON_ROUTE)
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_EXCEPTION_MSG)))
            .andExpect(jsonPath("$.error", containsString(UNAUTHORIZED_INVALID_KEY_MSG)))
    }

    private fun validateBadRequest(route: String): ResultActions {
        return mvc.perform(
            multipart(route)
                .contentType(MULTIPART_FORM_DATA_VALUE)
        )
    }

    private fun validateBadRequestNonTxtFile(route: String): ResultActions {
        val fileContent = "Text content of the file to be uploade"
        val mockFile = MockMultipartFile("file", "test.png", "text/plain", fileContent.toByteArray())

        return mvc.perform(
            multipart(route)
                .file(mockFile)
                .contentType(MULTIPART_FORM_DATA_VALUE)
        )
    }


    @Test
    fun `Must Returns NotFound on Grammar Reviewer TEXT`() {
        validateNotFoundRequest(GRAMMAR_REVIEWER_TEXT_ROUTE)
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error", `is`(NOT_FOUND_EXCEPTION_MSG)))
    }

    @Test
    fun `Must Returns NotFound on Grammar Reviewer JSON`() {
        validateNotFoundRequest(GRAMMAR_REVIEWER_JSON_ROUTE)
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("$.error", `is`(NOT_FOUND_EXCEPTION_MSG)))
    }

    private fun validateSuccessRequest(route: String): ResultActions {
        val fileContent = "Text content of the file to be upl"
        val mockFile = MockMultipartFile("file", "test.txt", "text/plain", fileContent.toByteArray())

        doReturn("Text content of the file to be uploaded").`when`(iGrammarReviewerService)
            .getGrammarReviewerService(fileContent)

        return mvc.perform(
            multipart(route)
                .file(mockFile)
                .contentType(MULTIPART_FORM_DATA_VALUE)
        )
    }

    private fun validateUnauthorizedRequest(route: String): ResultActions {
        val fileContent = "Text content of the file to be upl"
        val mockFile = MockMultipartFile("file", "test.txt", "text/plain", fileContent.toByteArray())

        doThrow(UnauthorizedException("User Unauthorized - Invalid Open AI key"))
            .`when`(iGrammarReviewerService).getGrammarReviewerService(fileContent)

        return mvc.perform(
            multipart(route)
                .file(mockFile)
                .contentType(MULTIPART_FORM_DATA_VALUE)
        )
    }

    private fun validateNotFoundRequest(route: String): ResultActions {
        val fileContent = "Text content of the file to be upl"
        val mockFile = MockMultipartFile("file", "test.txt", "text/plain", fileContent.toByteArray())

        doThrow(NotFoundException("No message"))
            .`when`(iGrammarReviewerService).getGrammarReviewerService(fileContent)

        return mvc.perform(
            multipart(route)
                .file(mockFile)
                .contentType(MULTIPART_FORM_DATA_VALUE)
        )
    }
}
