package com.example.articlehub.data.mapper

import com.example.articlehub.data.local.entity.BlogEntity
import com.example.articlehub.data.remote.dto.BlogDto
import com.example.articlehub.domain.model.Blog

fun BlogDto.toBlog() =  Blog(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    contentUrl = contentUrl,
    content = null
)

fun List<BlogDto>.toBlogList() = map { it.toBlog() }


fun BlogDto.toBlogEntity() =  BlogEntity(
    id = id,
    title = title,
    thumbnailUrl = thumbnailUrl,
    contentUrl = contentUrl
)

fun List<BlogDto>.toBlogEntityList() = map { it.toBlogEntity() }