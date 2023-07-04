package com.openai.student.helper.handler

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.*
import com.fasterxml.jackson.annotation.JsonInclude.Include.*

@JsonInclude(NON_EMPTY)
data class ErrorApiDTO(
    val error: String,
    val timestamp: String
)