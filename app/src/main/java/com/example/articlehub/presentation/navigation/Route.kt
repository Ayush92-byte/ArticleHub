package com.example.articlehub.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object BlogListScreen: Route
    @Serializable
    data class BlogContentScreen(val blogId: Int): Route

    @Serializable
    data object SavedBlogsScreen: Route
}