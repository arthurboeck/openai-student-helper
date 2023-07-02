package com.openai.student.helper

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@ActiveProfiles(profiles = ["test"])
abstract class BaseControllerTest {

    val CONSTRAINT_VIOLATION_EXCEPTION_MSG = "jakarta.validation.ConstraintViolationException:"
    val TOPIC_REQUIRED_MSG = "Topic is required"

    val UNAUTHORIZED_EXCEPTION_MSG = "com.openai.student.helper.infra.exceptions.UnauthorizedException:"
    val UNAUTHORIZED_INVALID_KEY_MSG = "User Unauthorized - Invalid Open AI key"

    @Autowired
    lateinit var mvc: MockMvc
}