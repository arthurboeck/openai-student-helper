package com.openai.student.helper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaleGeneratorApplication

fun main(args: Array<String>) {
	runApplication<TaleGeneratorApplication>(*args)
}