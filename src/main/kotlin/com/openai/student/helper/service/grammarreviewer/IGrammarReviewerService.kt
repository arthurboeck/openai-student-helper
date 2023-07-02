package com.openai.student.helper.service.grammarreviewer

fun interface IGrammarReviewerService {

    suspend fun getGrammarReviewerService(topic: String): String
}