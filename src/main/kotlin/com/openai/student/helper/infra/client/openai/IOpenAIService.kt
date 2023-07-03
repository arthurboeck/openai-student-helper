package com.openai.student.helper.infra.client.openai

fun interface IOpenAIService {
     fun integrateChatGpt(context: String, question: String): String
}