package com.example.urlshortener.infrastructure

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@Configuration
class RedisConfig {

    @Bean
    fun jedisConnectionFactory(
        configurationProperties: RedisConfigurationProperties,
    ): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration(configurationProperties.host, configurationProperties.port.toInt())
        val jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().build()
        val factory = JedisConnectionFactory(config, jedisClientConfiguration)
        factory.afterPropertiesSet()
        return factory
    }

    @Bean
    fun redisTemplate(
        configurationProperties: RedisConfigurationProperties,
    ): StringRedisTemplate {
        val template = StringRedisTemplate()
        template.connectionFactory = jedisConnectionFactory(configurationProperties)
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        return template
    }

    @ConfigurationProperties("spring.data.redis")
    data class RedisConfigurationProperties(
        val host: String,
        val port: String,
    )
}
