package com.openai.student.helper.infra.client

fun interface IOpenAIClient {
    suspend fun integrateChatGpt(context: String, question: String): String
}