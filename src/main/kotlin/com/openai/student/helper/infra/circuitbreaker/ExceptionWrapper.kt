package com.openai.student.helper.infra.circuitbreaker

import com.openai.student.helper.infra.exceptions.UnauthorizedException
import feign.FeignException
import javax.naming.ServiceUnavailableException

//abstract class ExceptionWrapper : (Throwable) -> Throwable {
//    override operator fun invoke(throwable: Throwable): Throwable
//        return if ((throwable.cause is UnauthorizedException) || (throwable.cause is FeignException.Unauthorized)) {
//            throw UnauthorizedException("User Unauthorized - ${throwable.cause}")
//        } else {
//            throw ServiceUnavailableException("Service unavailable for a while.")
//        }
//    }
//}
