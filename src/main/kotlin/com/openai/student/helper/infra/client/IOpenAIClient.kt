package com.openai.student.helper.infra.client

interface IOpenAIClient {
    suspend fun integrateChatGpt(context: String, question: String): String
}