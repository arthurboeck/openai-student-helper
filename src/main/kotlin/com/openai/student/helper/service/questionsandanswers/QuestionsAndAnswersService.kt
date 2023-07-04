package com.openai.student.helper.service.questionsandanswers

import com.openai.student.helper.infra.client.openai.IOpenAIService
import org.springframework.stereotype.Service

@Service
class QuestionsAndAnswersService(
    private val serviceClient: IOpenAIService
) : IQuestionsAndAnswersService {

    private val context: String = "Você é um auxiliar para estudos."

    override  fun getQuestionsAndAnswers(topic: String): String {
        val question = "Me sugira três perguntas e suas respostas sobre: \"$topic\""
        return serviceClient.integrateChatGpt(context, question)
    }
}