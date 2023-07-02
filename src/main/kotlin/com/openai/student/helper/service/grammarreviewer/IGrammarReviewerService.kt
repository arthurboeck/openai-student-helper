package com.openai.student.helper.service.grammarreviewer

fun interface IGrammarReviewerService {

    fun getGrammarReviewerService(topic: String): String
}