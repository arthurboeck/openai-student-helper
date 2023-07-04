package com.openai.student.helper.service.questionsandanswers

fun interface IQuestionsAndAnswersService {

    fun getQuestionsAndAnswers(topic: String): String
}