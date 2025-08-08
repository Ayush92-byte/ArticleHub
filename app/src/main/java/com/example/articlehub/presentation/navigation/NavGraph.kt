package com.example.articlehub.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.articlehub.presentation.blog_content.BlogContentScreen
import com.example.articlehub.presentation.blog_content.BlogContentViewModel
import com.example.articlehub.presentation.blog_list.BlogListScreen
import com.example.articlehub.presentation.blog_list.BlogListViewModel
import com.example.articlehub.presentation.blog_list.component.BlogCard
import com.example.articlehub.presentation.blog_saved.SavedBlogsScreen
import io.ktor.util.reflect.instanceOf
import org.koin.androidx.compose.koinViewModel

@SuppressLint("WrongNavigateRouteType", "UnrememberedGetBackStackEntry")
@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.BlogListScreen
    ) {
        composable<Route.BlogListScreen> {
            val parentEntry = remember { navController.getBackStackEntry(Route.BlogListScreen) }
            val viewModel = koinViewModel<BlogListViewModel>(viewModelStoreOwner = parentEntry)

//            val viewModel = koinViewModel<BlogListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            BlogListScreen(
                state = state,
                event = viewModel.events,
                onBlogCardClick = { id ->
                    navController.navigate(Route.BlogContentScreen(id))
                },
                onSavedClick = { navController.navigate(Route.SavedBlogsScreen) },
                filteredBlogs = viewModel.filteredBlogs.collectAsState().value,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                viewModel = viewModel
            )
        }
        composable<Route.BlogContentScreen> {
            val viewModel = koinViewModel<BlogContentViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            BlogContentScreen(
                state = state,
                onBackClick = { navController.navigateUp() },
                onAction = viewModel::onAction
            )
        }

        composable<Route.SavedBlogsScreen> {
            val parentEntry = remember { navController.getBackStackEntry(Route.BlogListScreen) }
            val viewModel = koinViewModel<BlogListViewModel>(viewModelStoreOwner = parentEntry)

//            val viewModel = koinViewModel<BlogListViewModel>()
            SavedBlogsScreen(
                viewModel = viewModel,
                onBlogCardClick = { id ->
                    navController.navigate(Route.BlogContentScreen(id))
                },
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}