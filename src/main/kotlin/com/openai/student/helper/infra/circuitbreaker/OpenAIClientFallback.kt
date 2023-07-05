package com.openai.student.helper.infra.circuitbreaker

import com.openai.student.helper.infra.client.openai.IOpenAIClient
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionRequestDTO
import com.openai.student.helper.infra.client.openai.dto.ChatCompletionResponseDTO
import com.openai.student.helper.infra.exceptions.UnauthorizedException
import feign.FeignException
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import javax.naming.ServiceUnavailableException

//@Component
//class OpenAIClientFallback(
//        private val exception: Throwable
//) : IOpenAIClient {
//
//    override fun chatCompletion(@RequestHeader(value = "Authorization") token: String, @RequestBody request: ChatCompletionRequestDTO): ChatCompletionResponseDTO {
//        return if ((exception.cause is UnauthorizedException) || (exception.cause is FeignException.Unauthorized)) {
//            throw UnauthorizedException("User Unauthorized - ${exception.cause}")
//        } else {
//            throw ServiceUnavailableException("Service unavailable for a while.")
//        }
//    }
//}