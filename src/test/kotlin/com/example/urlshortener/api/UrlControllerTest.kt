package com.example.urlshortener.api

import com.example.urlshortener.FULL_URL
import com.example.urlshortener.domain.UrlService
import com.example.urlshortener.hashUrl
import com.ninjasquad.springmockk.MockkBean
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UrlController::class)
class UrlControllerTest {
    @MockkBean
    private lateinit var urlService: UrlService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `should save url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            urlService.saveUrl(FULL_URL)
        } returns givenHash

        mockMvc.perform(
            post("/url")
                .content(
                    """
                        {
                            "fullUrl": "$FULL_URL"
                        }
                    """.trimIndent(),
                )
                .contentType(MediaType.APPLICATION_JSON),
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(givenHash),
            )
    }

    @Test
    fun `should get shortened url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            urlService.getHash(FULL_URL)
        } returns givenHash

        mockMvc.perform(
            get("/url/short")
                .param("fullUrl", FULL_URL),
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(givenHash),
            )
    }

    @Test
    fun `should return 404 while get shortened url`() {
        every {
            urlService.getHash(FULL_URL)
        } returns null

        mockMvc.perform(
            get("/url/short")
                .param("fullUrl", FULL_URL),
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should resolve url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            urlService.getFullUrl(givenHash)
        } returns FULL_URL

        mockMvc.perform(
            get("/url/resolve/$givenHash"),
        )
            .andExpect(status().isOk)
            .andExpect(
                content().string(FULL_URL),
            )
    }

    @Test
    fun `should return 404 while resolving url`() {
        val givenHash = hashUrl(FULL_URL)
        every {
            urlService.getFullUrl(givenHash)
        } returns null

        mockMvc.perform(
            get("/url/resolve/$givenHash"),
        )
            .andExpect(status().isNotFound)
    }
}
