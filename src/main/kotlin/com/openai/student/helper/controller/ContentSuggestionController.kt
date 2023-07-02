package com.openai.student.helper.controller

import com.openai.student.helper.infra.client.MessageDTO
import com.openai.student.helper.service.contentsuggestion.IContentSuggestionService
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
class ContentSuggestionController(private val contentSuggestion: IContentSuggestionService){

    @Operation(summary = "Contents for study based on topic", description = "Returns three contents for study based on the inputted topic in application/json content type.")
    @GetMapping("/content-suggestion/text")
    suspend fun contentSuggestionsInText(
        @NotBlank(message = "Topic is required")
        @Pattern(regexp = "\\S+", message = "Topic cannot be empty or contain only whitespace")
        @RequestParam("topic") topic: String): String {
        return contentSuggestion.getContentSuggestion(topic)
    }

    @Operation(summary = "Contents for study based on topic", description = "Returns three contents for study based on the inputted topic in application/json content type.")
    @GetMapping("/content-suggestion/json")
    suspend fun contentSuggestionsInJson(
        @NotBlank(message = "Topic is required")
        @Pattern(regexp = "\\S+", message = "Topic cannot be empty or contain only whitespace")
        @RequestParam("topic") topic: String): MessageDTO {
        return MessageDTO(contentSuggestion.getContentSuggestion(topic))
    }
}