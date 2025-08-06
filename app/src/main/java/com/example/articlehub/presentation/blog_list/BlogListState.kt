package com.example.articlehub.presentation.blog_list

import com.example.articlehub.domain.model.Blog

data class BlogListState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val blogs: List<Blog> = emptyList()
)