package com.openai.student.helper.service.topicquestions

import com.openai.student.helper.infra.client.OpenAIClient
import org.springframework.stereotype.Service

@Service
class TopicQuestionsService(private val serviceClient: OpenAIClient) : ITopicQuestionsService {

    private val context: String = "Você é um auxiliar para estudos."

    override  fun getTopicQuestions(topic: String): String {
        val question = "Me sugira três perguntas e suas respostas sobre: \"$topic\""
        return serviceClient.integrateChatGpt(context, question)
    }
}