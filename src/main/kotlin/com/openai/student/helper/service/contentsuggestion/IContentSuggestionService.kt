package com.openai.student.helper.service.contentsuggestion

fun interface IContentSuggestionService {

    suspend fun getContentSuggestion(topic: String): String
}