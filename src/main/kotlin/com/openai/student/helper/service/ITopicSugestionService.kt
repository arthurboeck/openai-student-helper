package com.openai.student.helper.service

fun interface ITopicSugestionService {

    suspend fun getTopicSugestion(topic: String): String
}