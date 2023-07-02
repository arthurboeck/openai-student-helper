package com.openai.student.helper.controller

import com.openai.student.helper.infra.client.MessageDTO
import com.openai.student.helper.service.contentsuggestion.IContentSuggestionService
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.constraints.NotBlank
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.TEXT_PLAIN_VALUE
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/v1/open-ai/student-helper")
class ContentSuggestionController(private val contentSuggestion: IContentSuggestionService){

    @Operation(summary = "Contents for study based on topic", description = "Returns three contents for study based on the inputted topic in text/plain content type.")
    @GetMapping("/content-suggestion/text", produces = [TEXT_PLAIN_VALUE])
    fun contentSuggestionsInText(
        @NotBlank(message = "Topic is required")
        @RequestParam("topic") topic: String): String {
        return contentSuggestion.getContentSuggestion(topic)
    }

    @Operation(summary = "Contents for study based on topic", description = "Returns three contents for study based on the inputted topic in application/json content type.")
    @GetMapping("/content-suggestion/json", produces = [APPLICATION_JSON_VALUE])
    fun contentSuggestionsInJson(
        @NotBlank(message = "Topic is required")
        @RequestParam("topic") topic: String): MessageDTO {
        return MessageDTO(contentSuggestion.getContentSuggestion(topic))
    }
}