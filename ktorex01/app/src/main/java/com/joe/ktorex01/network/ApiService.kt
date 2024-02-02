package com.joe.ktorex01.network

import com.joe.ktorex01.models.RequestModel
import com.joe.ktorex01.models.ResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.logging.*

interface ApiService {
    suspend fun getProducts(): List<ResponseModel>
    suspend fun createProducts(productRequest: RequestModel): ResponseModel?

    companion object {
        fun create(): ApiService {
            return ApiServiceImpl(
//                client = HttpClient(Android) {
                client = HttpClient(CIO) {
                    // logging
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    // json
                    install(JsonFeature) {
//                        serializer = KotlinxSerializer(json)
                         serializer = KotlinxSerializer()
                    }
                    // timeout
                    install(HttpTimeout) {
                        requestTimeoutMillis = 15000L
                        connectTimeoutMillis = 15000L
                        socketTimeoutMillis = 15000L
                    }
                    // apply to all requests
                    defaultRequest {
                        if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }
                }

            )
        }

        private val json = kotlinx.serialization.json.Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = false
        }
    }
}