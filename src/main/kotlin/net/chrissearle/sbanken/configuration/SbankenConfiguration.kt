package net.chrissearle.sbanken.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "sbanken")
data class SbankenConfiguration(
    val clientId: String,
    val clientSecret: String,
    val authUrl: String,
    val apiBase: String,
    val apiVersion: String
)
