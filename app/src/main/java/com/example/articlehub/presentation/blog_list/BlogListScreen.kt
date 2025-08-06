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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.articlehub.domain.model.Blog
import com.example.articlehub.presentation.blog_list.component.BlogCard
import com.example.articlehub.presentation.common_component.ShimmerEffect
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogListScreen(
    modifier: Modifier = Modifier,
    state: BlogListState,
    event: Flow<BlogListEvent>,
    filteredBlogs: List<Blog>,
    onSearchQueryChange: (String) -> Unit,
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

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Column (
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection)
    ){
        BlogListTopBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            scrollBehavior = scrollBehaviour
        )
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
                items(filteredBlogs) { blog ->
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
private fun BlogListTopBar(
    searchQuery: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            windowInsets = WindowInsets(0),
            modifier = modifier,
            title = { Text(text = "Android Blogs") }
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("Search blogs...") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            }
        )
    }
}
