package com.joe.ktorex01.network

import android.util.Log
import com.joe.ktorex01.models.RequestModel
import com.joe.ktorex01.models.ResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.features.get
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol

class ApiServiceImpl (
    private val client: HttpClient
) : ApiService {
    override suspend fun getProducts(): List<ResponseModel> {
        return try {
            client.get {
//                url(ApiRoutes.PRODUCTS)
                url {
                    protocol = URLProtocol.HTTPS
                    host = "fakestoreapi.com"
//                    encodedPath = "/products"
                    path("/products")
//                    parameters.append("since", "2020-07-17")
                }
            }
        } catch (e:RedirectResponseException) {
            // 3xx error
            Log.i("ktorex01", "Error ${e.response.status.description}")
            emptyList()
        } catch (e: ClientRequestException) {
            // 4xx error
            Log.i("ktorex01", "Error ${e.response.status.description}")
            emptyList()
        } catch (e: ServerResponseException) {
            // 5xx error
            Log.i("ktor01", "${e.response.status.description}")
            emptyList()
        }
    }

    override suspend fun createProducts(productRequest: RequestModel): ResponseModel? {
        return try {
            client.post<ResponseModel> {
//                url(ApiRoutes.PRODUCTS)
                url {
                    protocol = URLProtocol.HTTPS
                    host = "fakestoreapi.com"
//                    encodedPath = "/products"
                    path("/products")
//                    parameters.append("since", "2020-07-17")
                }
                body = productRequest
            }
        } catch (e: RedirectResponseException) {
            // 3xx error
            Log.i("ktor01", "Error: ${e.response.status.description}")
            null
        } catch (e: ClientRequestException) {
            // 4xx error
            Log.i("ktorex01", "Error: ${e.response.status.description}")
            null
        } catch (e: ServerResponseException) {
            // 5xx error
            Log.i("ktorex01", "Error ${e.response.status.description}")
            null
        }
    }

}