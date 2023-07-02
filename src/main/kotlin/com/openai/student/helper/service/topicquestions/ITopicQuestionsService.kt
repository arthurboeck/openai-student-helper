package com.openai.student.helper.service.topicquestions

fun interface ITopicQuestionsService {

    suspend fun getTopicQuestions(topic: String): String
}