package com.example.articlehub.data.remote

import com.example.articlehub.data.util.Constant.GITHUB_URL
import com.example.articlehub.data.remote.dto.BlogDto
import com.example.articlehub.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import java.net.UnknownHostException

class KtorRemoteBlogDataSource(
    private val httpClient: HttpClient
) : RemoteBlogDataSource{

    override suspend fun getAllBlogs(): Result<List<BlogDto>> {
        return try {
            val response = httpClient.get(urlString = GITHUB_URL)
            val blogs = response.body<List<BlogDto>>()
            Result.Success(blogs)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Result.Error(message = "Network Error. Please check your internet connection.")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = "Something went wrong. ${e.message}")
        }
    }

    override suspend fun fetchBlogContent(url: String): Result<String> {
        return try {
            val response = httpClient.get(urlString = url)
            val blogContent = response.bodyAsText()
            Result.Success(blogContent)
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Result.Error(message = "Network Error. Please check your internet connection.")
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(message = "Something went wrong. ${e.message}")
        }
    }
}