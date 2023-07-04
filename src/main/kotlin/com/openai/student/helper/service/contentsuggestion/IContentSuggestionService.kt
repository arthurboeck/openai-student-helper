package com.openai.student.helper.service.contentsuggestion

fun interface IContentSuggestionService {

    fun getContentSuggestion(topic: String): String
}