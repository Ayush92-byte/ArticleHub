package com.example.articlehub.data.remote

import com.example.articlehub.data.remote.dto.BlogDto
import com.example.articlehub.domain.util.Result

interface RemoteBlogDataSource {

    suspend fun getAllBlogs(): Result<List<BlogDto>>

    suspend fun fetchBlogContent(url: String): Result<String>
}