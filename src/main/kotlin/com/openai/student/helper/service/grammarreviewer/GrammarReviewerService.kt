package com.openai.student.helper.service.grammarreviewer

import com.openai.student.helper.infra.client.IOpenAIClient
import org.springframework.stereotype.Service

@Service
class GrammarReviewerService(private val serviceClient: IOpenAIClient) : IGrammarReviewerService {

    private val context: String = "Você é um revisor de textos."

    override fun getGrammarReviewerService(fileText: String): String {
        val question = "Revise o texto a seguir e sugira melhorias gramaticais, retornando apenas o texto corrido: \"$fileText\""
        val reviewedText = serviceClient.integrateChatGpt(context, question)

        return "Este é o texto ajustado, contendo as minhas sugestões de melhoria: \n\n $reviewedText"
    }
}