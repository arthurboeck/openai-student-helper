package com.openai.student.helper.controller

import com.openai.student.helper.infra.client.MessageDTO
import com.openai.student.helper.infra.exceptions.InvalidFileTypeException
import com.openai.student.helper.service.grammarreviewer.IGrammarReviewerService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Validated
@RestController
@RequestMapping("/v1/open-ai/student-helper")
class GrammarReviewerController(private val grammarReviewer: IGrammarReviewerService){

    @Operation(summary = "Grammar Reviewer based on txt file", description = "Returns the text from your txt file with the suggested review in text/plain content type.")
    @PostMapping("/grammar-reviewer/text", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun topicSuggestionsInText(
        @RequestPart("file") file: MultipartFile): String {
        return grammarReviewer.getGrammarReviewerService(extractFileContent(file))
    }

    @Operation(summary = "Grammar Reviewer based on txt file", description = "Returns the text from your txt file with the suggested review in application/json content type.")
    @PostMapping("/grammar-reviewer/json", consumes = [MULTIPART_FORM_DATA_VALUE])
    fun topicSuggestionsInJson(
        @RequestPart("file") file: MultipartFile): MessageDTO {
        return MessageDTO(grammarReviewer.getGrammarReviewerService(extractFileContent(file)))
    }

    private fun extractFileContent(file: MultipartFile): String {
        if (!file.originalFilename?.endsWith(".txt")!!) {
            throw InvalidFileTypeException("The file must be a file of type: .txt")
        }
        return file.inputStream.bufferedReader().use { it.readText() }
    }
}