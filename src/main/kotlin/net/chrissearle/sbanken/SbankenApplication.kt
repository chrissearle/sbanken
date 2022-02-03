package net.chrissearle.sbanken

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SbankenApplication

fun main(args: Array<String>) {
	runApplication<SbankenApplication>(*args)
}
