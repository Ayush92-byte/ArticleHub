package com.example.articlehub.presentation.blog_content

import com.example.articlehub.domain.model.Blog

data class BlogContentState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val blog: Blog? = null
)