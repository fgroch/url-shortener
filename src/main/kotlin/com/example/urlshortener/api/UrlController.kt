package com.example.urlshortener.api

import org.springframework.http.ResponseEntity

class UrlController : UrlApi {
    override fun shortenedUrl(fillUrl: String): ResponseEntity<String> {
        TODO("Not yet implemented")
    }

    override fun save(urlDto: UrlDto): ResponseEntity<String> {
        TODO("Not yet implemented")
    }
}
