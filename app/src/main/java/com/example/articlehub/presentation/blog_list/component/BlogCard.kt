package com.example.articlehub.presentation.blog_list.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import coil3.compose.AsyncImage
import com.example.articlehub.R
import com.example.articlehub.domain.model.Blog

@Composable
fun BlogCard(
    modifier: Modifier = Modifier,
    blog: Blog,
    onSaveClick: (Blog) -> Unit
) {
    Card(modifier = modifier) {
        Column {
            BlogCardImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(ratio = 2f),
                imageUrl = blog.thumbnailUrl
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = blog.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = { onSaveClick(blog) }) {
                    Icon(
                        imageVector = if(blog.isSaved)
                            Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if(blog.isSaved) "UnSave" else "Save"
                    )
                }
            }
        }
    }
}

@Composable
private fun BlogCardImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest
        .Builder(context)
        .data(imageUrl)
        .crossfade(enable = true)
        .build()

    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.img_placeholder),
            error = painterResource(id = R.drawable.img_placeholder),
        )
    }
}