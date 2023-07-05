package com.openai.student.helper.infra.circuitbreaker


import com.openai.student.helper.infra.client.openai.IOpenAIClient
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.feign.FeignDecorators
import io.github.resilience4j.feign.Resilience4jFeign
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class OpenAIClientDecorator {

    private val openaiUrl = "\${service.open-ai.url}"

    @Bean
    fun decoratorService(): IOpenAIClient {
        val circuitBreaker = CircuitBreaker.ofDefaults("openai-client")
        val decorators = FeignDecorators.builder()
                .withCircuitBreaker(circuitBreaker)
                .build()

        return Resilience4jFeign.builder(decorators).target(IOpenAIClient::class.java, openaiUrl);
    }
}