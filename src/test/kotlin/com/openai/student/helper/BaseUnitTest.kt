package com.openai.student.helper

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@ActiveProfiles(profiles = ["test"])
abstract class BaseUnitTest {

    val CONSTRAINT_VIOLATION_EXCEPTION_MSG = "jakarta.validation.ConstraintViolationException:"
    val TOPIC_REQUIRED_MSG = "Topic is required"

    val REQUEST_PART_EXCEPTION_MSG = "org.springframework.web.multipart.support.MissingServletRequestPartException:"
    val REQUIRED_PART_NOT_PRESENT_MSG = "Required part 'file' is not present"

    val INVALID_FILE_TYPE_EXCEPTION_MSG = "com.openai.student.helper.infra.exceptions.InvalidFileTypeException:"
    val INVALID_FILE_MSG = "The file must be a file of type: .txt"

    val UNAUTHORIZED_EXCEPTION_MSG = "com.openai.student.helper.infra.exceptions.UnauthorizedException:"
    val UNAUTHORIZED_INVALID_KEY_MSG = "User Unauthorized - Invalid Open AI key"
}