package com.example.urlshortener.api

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import java.net.HttpURLConnection

@Tag(name = "url", description = "Url operations")
interface UrlApi {
    @Operation(
        summary = "Get shortened url.",
    )
    @ApiResponses(
        ApiResponse(responseCode = HttpURLConnection.HTTP_OK.toString(), description = "Successful operation"),
    )
    fun shortenedUrl(@Parameter(description = "Full url", `in` = ParameterIn.QUERY) fillUrl: String): ResponseEntity<String>

    @Operation(
        summary = "Creates shortened url",
    )
    @ApiResponses(
        ApiResponse(responseCode = HttpURLConnection.HTTP_OK.toString(), description = "Successful operation"),
    )
    fun save(
        @RequestBody(description = "Url request body") urlDto: UrlDto,
    ): ResponseEntity<String>
}
