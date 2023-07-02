package com.openai.student.helper.controller

import com.openai.student.helper.infra.client.MessageDTO
import com.openai.student.helper.service.ITopicSugestionService
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
class StudentHelperController(private val topicSugestion: ITopicSugestionService){

    @GetMapping("/topic-sugestion/text")
    suspend fun topicSuggestorInText(
        @NotBlank(message = "Topic is required")
        @Pattern(regexp = "\\S+", message = "Topic cannot be empty or contain only whitespace")
        @RequestParam("topic") topic: String): String {
        return topicSugestion.getTopicSugestion(topic)
    }

    @GetMapping("/topic-sugestion/json")
    suspend fun topicSuggestorInJson(
        @NotBlank(message = "Topic is required")
        @Pattern(regexp = "\\S+", message = "Topic cannot be empty or contain only whitespace")
        @RequestParam("topic") topic: String): MessageDTO {
        return MessageDTO(topicSugestion.getTopicSugestion(topic))
    }
}