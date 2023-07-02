package com.openai.student.helper.infra.client

fun interface IOpenAIClient {
     fun integrateChatGpt(context: String, question: String): String
}