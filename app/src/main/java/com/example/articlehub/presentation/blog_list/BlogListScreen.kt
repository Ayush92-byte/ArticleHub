package com.example.articlehub.presentation.blog_list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.articlehub.domain.model.Blog
import com.example.articlehub.presentation.blog_list.component.BlogCard
import com.example.articlehub.presentation.common_component.ShimmerEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun BlogListScreen(
    modifier: Modifier = Modifier,
    state: BlogListState,
    event: Flow<BlogListEvent>,
    onBlogCardClick: (Int) -> Unit
) {

    val context = LocalContext.current
    LaunchedEffect (key1 = Unit){
        event.collect { event ->
            when(event) {
                is BlogListEvent.Error -> {
                    Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column (
        modifier = modifier.fillMaxSize()
    ){
        BlogListTopBar()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if(state.isLoading) {
                items(count = 3) {
                    ShimmerEffect(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                    )
                }
            } else {
                items(state.blogs) { blog ->
                    BlogCard(
                        modifier = Modifier.clickable { onBlogCardClick(blog.id) },
                        blog = blog
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BlogListTopBar (
    modifier: Modifier = Modifier
) {
    TopAppBar(
        windowInsets = WindowInsets(0),
        modifier = modifier,
        title = { Text(text = "Android Blogs")}
    )
}