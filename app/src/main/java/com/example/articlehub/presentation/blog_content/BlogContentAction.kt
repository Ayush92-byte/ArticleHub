package com.example.articlehub.presentation.blog_content

sealed interface BlogContentAction {
    data object Refresh: BlogContentAction
}