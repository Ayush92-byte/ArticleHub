package com.example.articlehub.data.mapper

import com.example.articlehub.data.local.entity.BlogEntity
import com.example.articlehub.domain.model.Blog

fun BlogEntity.toBlog(
    content: String? = null
) = Blog(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    contentUrl = contentUrl,
    content = content
)

fun List<BlogEntity>.toBlogList() = map { it.toBlog() }