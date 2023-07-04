package com.openai.student.helper.controller

import com.openai.student.helper.infra.MessageDTO
import com.openai.student.helper.service.questionsandanswers.IQuestionsAndAnswersService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.constraints.NotBlank
import org.springframework.http.MediaType.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/v1/open-ai/student-helper")
class QuestionsAndAnswersController(
    private val iQuestionsAndAnswersService: IQuestionsAndAnswersService
){

    @Operation(summary = "Questions and answers based on topic for study", description = "Returns three questions and answers for study based on the inputted topic in text/plain content type.")
    @GetMapping("/question-answer/text", produces = [TEXT_PLAIN_VALUE])
    fun topicQuestionsInText(
        @NotBlank(message = "Topic is required")
        @RequestParam("topic") topic: String): String {
        return iQuestionsAndAnswersService.getQuestionsAndAnswers(topic)
    }


    @Operation(summary = "Questions and answers based on topic for study", description = "Returns three questions and answers for study based on the inputted topic in application/json content type.")
    @GetMapping("/question-answer/json", produces = [APPLICATION_JSON_VALUE])
    fun topicQuestionsInJson(
        @NotBlank(message = "Topic is required")
        @RequestParam("topic") topic: String): MessageDTO {
        return MessageDTO(iQuestionsAndAnswersService.getQuestionsAndAnswers(topic))
    }
}