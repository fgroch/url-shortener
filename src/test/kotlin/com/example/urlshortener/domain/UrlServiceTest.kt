package com.example.urlshortener.domain

import com.example.urlshortener.FULL_URL
import com.example.urlshortener.hashUrl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.redis.core.StringRedisTemplate

class UrlServiceTest {

    @MockK
    private lateinit var redisTemplate: StringRedisTemplate

    @InjectMockKs
    private lateinit var urlService: UrlService

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `should save url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            redisTemplate.opsForValue().get(givenHash)
        } returns null
        every {
            redisTemplate.opsForValue().set(givenHash, FULL_URL)
        } just runs

        val url = urlService.saveUrl(FULL_URL)

        assertThat(url).isEqualTo(hashUrl(FULL_URL))
    }

    @Test
    fun `should get shortened url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            redisTemplate.opsForValue().get(givenHash)
        } returns FULL_URL

        val url = urlService.getHash(FULL_URL)

        assertThat(url).isEqualTo(givenHash)
    }

    @Test
    fun `should resolve url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            redisTemplate.opsForValue().get(givenHash)
        } returns FULL_URL

        val url = urlService.getFullUrl(givenHash)

        assertThat(url).isEqualTo(FULL_URL)
    }
}
