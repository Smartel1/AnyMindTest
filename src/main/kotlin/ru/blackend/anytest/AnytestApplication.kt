package ru.blackend.anytest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AnytestApplication

fun main(args: Array<String>) {
	runApplication<AnytestApplication>(*args)
}
