package com.example.articlehub.domain.repository

import com.example.articlehub.domain.model.Blog
import com.example.articlehub.domain.util.Result

interface BlogRepository {

    suspend fun getAllBlogs(): Result<List<Blog>>

    suspend fun getBlogById(blogId: Int): Result<Blog>
}