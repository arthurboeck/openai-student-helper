package com.openai.student.helper.service.topicquestions

fun interface ITopicQuestionsService {

    fun getTopicQuestions(topic: String): String
}