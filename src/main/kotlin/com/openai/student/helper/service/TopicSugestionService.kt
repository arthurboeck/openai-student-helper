package com.openai.student.helper.service

import com.openai.student.helper.infra.client.OpenAIClient
import org.springframework.stereotype.Service

@Service
class TopicSugestionService(private val serviceClient: OpenAIClient) : ITopicSugestionService {

    private val context: String = "Você é um auxiliar para estudos."

    override suspend fun getTopicSugestion(topic: String): String {
        val question = "Me sugira três pontos chave para estudar sobre: \"$topic\""
        return serviceClient.integrateChatGpt(context, question)
    }
}