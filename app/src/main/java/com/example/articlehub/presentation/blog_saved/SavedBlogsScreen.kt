package com.example.articlehub.presentation.blog_saved

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.articlehub.domain.model.Blog
import com.example.articlehub.presentation.blog_list.BlogListViewModel
import com.example.articlehub.presentation.blog_list.component.BlogCard
import com.example.articlehub.presentation.common_component.ShimmerEffect
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedBlogsScreen(
    modifier: Modifier = Modifier,
    viewModel: BlogListViewModel,
    onBlogCardClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val savedBlogs by viewModel.savedBlogs.collectAsState()

    Log.d("SavedBlogsScreen", "Saved Blogs: ${savedBlogs.map {it.title}}")

    Column(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        SavedBlogTopBar(
            scrollBehavior = scrollBehavior,
            onBackClick = onBackClick
        )
        if(savedBlogs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No blogs saved yet")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 300.dp),
                contentPadding = PaddingValues(15.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(savedBlogs) { blog ->
                    BlogCard(
                        modifier = Modifier.clickable { onBlogCardClick(blog.id) },
                        blog = blog,
                        onSaveClick = { viewModel.toggleSaveBlog(it) }
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SavedBlogTopBar (
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        windowInsets = WindowInsets(0),
        modifier = modifier,
        title = {
            Text(
                text = "Saved Blogs",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        }
    )
}