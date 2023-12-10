package com.example.urlshortener.domain

import com.google.common.hash.Hashing
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets.UTF_8

@Service
class UrlService(
    val redisTemplate: StringRedisTemplate,
) {
    fun getHash(fullUrl: String): String? =
        hashUrl(fullUrl).takeIf { hash -> getFullUrl(hash) != null }

    fun getFullUrl(hash: String): String? =
        redisTemplate.opsForValue().get(hash)

    fun saveUrl(fullUrl: String): String {
        val hash = hashUrl(fullUrl)
        hash.takeUnless { getFullUrl(hash) != null }?.let { hashedUrl ->
            redisTemplate.opsForValue().set(hashedUrl, fullUrl)
        }
        return hash
    }

    private fun hashUrl(url: String) = Hashing.murmur3_32_fixed().hashString(url, UTF_8).toString()
}
