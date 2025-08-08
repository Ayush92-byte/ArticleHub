package com.example.articlehub.presentation.blog_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.articlehub.domain.model.Blog
import com.example.articlehub.domain.repository.BlogRepository
import com.example.articlehub.domain.util.Result
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlogListViewModel(
    private val blogRepository: BlogRepository
) : ViewModel() {

    private val _state = MutableStateFlow(BlogListState())
    val state = _state
        .onStart {
            getAllBlogs()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    private val _events = Channel<BlogListEvent>()
    val events = _events.receiveAsFlow()

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    val filteredBlogs: StateFlow<List<Blog>> = _state.map { state ->
        if (state.searchQuery.isBlank()) {
            state.blogs
        } else {
            state.blogs.filter {
                it.title.contains(state.searchQuery, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList())


    private val _savedBlogs = MutableStateFlow<List<Blog>>(emptyList())
    val savedBlogs: StateFlow<List<Blog>> = _savedBlogs.asStateFlow()

    fun toggleSaveBlog(blog: Blog) {
        val updatedList = _state.value.blogs.map {
            if (it.id == blog.id) it.copy(isSaved = !it.isSaved) else it
        }

        _state.update { it.copy(blogs = updatedList) }

        _savedBlogs.value = updatedList.filter { it.isSaved }
    }


    private fun getAllBlogs() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = blogRepository.getAllBlogs()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            blogs = result.data.orEmpty().reversed(),
                            errorMessage = null,
                            isLoading = false
                        )
                    }
                }

                is Result.Error -> {
                    _state.update {
                        it.copy(
                            blogs = result.data.orEmpty().reversed(),
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                    result.message?.let {
                        _events.send(BlogListEvent.Error(it))
                    }
                }
            }
        }
    }
}