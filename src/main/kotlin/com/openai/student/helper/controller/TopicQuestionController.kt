package com.openai.student.helper.controller

import com.openai.student.helper.infra.client.MessageDTO
import com.openai.student.helper.service.topicquestions.ITopicQuestionsService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/v1/open-ai/student-helper")
class TopicQuestionController(private val topicQuestion: ITopicQuestionsService){

    @Operation(summary = "Questions and answers based on topic for study", description = "Returns three questions and answers for study based on the inputted topic in application/json content type.")
    @GetMapping("/topic-question/text")
    suspend fun topicQuestionsInText(
        @NotBlank(message = "Topic is required")
        @Pattern(regexp = "\\S+", message = "Topic cannot be empty or contain only whitespace")
        @RequestParam("topic") topic: String): String {
        return topicQuestion.getTopicQuestions(topic)
    }


    @Operation(summary = "Questions and answers based on topic for study", description = "Returns three questions and answers for study based on the inputted topic in application/json content type.")
    @GetMapping("/topic-question/json")
    suspend fun topicQuestionsInJson(
        @NotBlank(message = "Topic is required")
        @Pattern(regexp = "\\S+", message = "Topic cannot be empty or contain only whitespace")
        @RequestParam("topic") topic: String): MessageDTO {
        return MessageDTO(topicQuestion.getTopicQuestions(topic))
    }
}