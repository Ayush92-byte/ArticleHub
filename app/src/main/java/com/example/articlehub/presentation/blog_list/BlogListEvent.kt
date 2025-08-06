package com.example.articlehub.presentation.blog_list

sealed interface BlogListEvent {
    data class Error(val error: String): BlogListEvent
}