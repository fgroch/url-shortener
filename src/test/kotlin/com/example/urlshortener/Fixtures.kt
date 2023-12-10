package com.example.urlshortener

import com.google.common.hash.Hashing
import java.nio.charset.StandardCharsets

fun hashUrl(url: String) = Hashing.murmur3_32_fixed().hashString(url, StandardCharsets.UTF_8).toString()

const val FULL_URL = "com.example"
