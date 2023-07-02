package com.openai.student.helper.service.contentsuggestion

import com.openai.student.helper.infra.client.OpenAIClient
import org.springframework.stereotype.Service

@Service
class ContentSuggestionService(private val serviceClient: OpenAIClient) : IContentSuggestionService {

    private val context: String = "Você é um auxiliar para estudos."

    override fun getContentSuggestion(topic: String): String {
        val question = "Me sugira três pontos chave para estudar sobre: \"$topic\""
        return serviceClient.integrateChatGpt(context, question)
    }
}