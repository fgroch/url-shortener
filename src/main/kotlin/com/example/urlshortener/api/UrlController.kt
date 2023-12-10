package com.example.urlshortener.api

import com.example.urlshortener.domain.UrlService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/url")
class UrlController(
    private val urlService: UrlService,
) : UrlApi {
    @GetMapping("/short")
    override fun shortenedUrl(@RequestParam fullUrl: String): ResponseEntity<String> =
        urlService.getHash(fullUrl)?.let { hash ->
            ResponseEntity.ok(hash)
        } ?: ResponseEntity.notFound().build()

    @GetMapping("/resolve/{shortenedUrl}")
    override fun resolveUrl(@PathVariable shortenedUrl: String): ResponseEntity<String> =
        urlService.getFullUrl(shortenedUrl)?.let { fullUrl ->
            ResponseEntity.ok(fullUrl)
        } ?: ResponseEntity.notFound().build()

    @PostMapping
    override fun save(@RequestBody urlDto: UrlDto): ResponseEntity<String> {
        val shortenedUrl = urlService.saveUrl(urlDto.fullUrl)
        return ResponseEntity.ok(shortenedUrl)
    }
}
