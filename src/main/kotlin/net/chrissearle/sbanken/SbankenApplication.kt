package net.chrissearle.sbanken

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("net.chrissearle.sbanken.configuration")
class SbankenApplication

fun main(args: Array<String>) {
	runApplication<SbankenApplication>(*args)
}
