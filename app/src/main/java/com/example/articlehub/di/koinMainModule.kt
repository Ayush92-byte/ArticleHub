package com.example.articlehub.di

import com.example.articlehub.data.local.BlogDatabase
import com.example.articlehub.data.local.DatabaseFactory
import com.example.articlehub.data.remote.HttpClientFactory
import com.example.articlehub.data.remote.KtorRemoteBlogDataSource
import com.example.articlehub.data.remote.RemoteBlogDataSource
import com.example.articlehub.data.repository.BlogRepositoryImpl
import com.example.articlehub.domain.repository.BlogRepository
import com.example.articlehub.presentation.blog_content.BlogContentViewModel
import com.example.articlehub.presentation.blog_list.BlogListViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinMainModule = module {

    single { DatabaseFactory.create(get()) }

    single { get<BlogDatabase>().blogDao() }

    single { HttpClientFactory.create(OkHttp.create()) }

    singleOf(::KtorRemoteBlogDataSource).bind<RemoteBlogDataSource>()

    singleOf(::BlogRepositoryImpl).bind<BlogRepository>()

    viewModelOf(::BlogListViewModel)

    viewModelOf(::BlogContentViewModel)
}