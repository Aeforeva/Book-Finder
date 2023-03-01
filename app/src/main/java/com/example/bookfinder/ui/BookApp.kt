package com.example.bookfinder.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bookfinder.R
import com.example.bookfinder.ui.layouts.BookContentType

enum class BookScreen() {
    Search, // Starter screen
    Main, // Search result scree
    Detail // Detail book info screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookApp(windowSize: WindowWidthSizeClass, modifier: Modifier = Modifier) {

    val viewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookScreen.valueOf(
        backStackEntry?.destination?.route ?: BookScreen.Search.name
    )
    var searchedBookTitle by rememberSaveable { mutableStateOf("") }
    val selectedBook by viewModel.selectedBook.collectAsState()

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            BookContentType.LIST_ONLY
        }
        else -> {
            BookContentType.LIST_AND_DETAIL
        }
    }

    Scaffold(
        topBar = {
            BookAppBar(
                currentScreen = currentScreen,
                searchedBookTitle = searchedBookTitle,
                selectedBookTitle = selectedBook.volumeInfo.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookScreen.Search.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BookScreen.Search.name) {
                BookSearchScreen(
                    searchedBookTitle = searchedBookTitle,
                    onValueChanged = { searchedBookTitle = it },
                    onSearch = {
                        viewModel.getBooks(it)
                        navController.navigate(BookScreen.Main.name)
                    }
                )
            }
            composable(route = BookScreen.Main.name) {
                when (contentType) {
                    BookContentType.LIST_ONLY -> BookMainScreen(
                        bookUiState = viewModel.bookUiState,
                        retryAction = { viewModel.getBooks(searchedBookTitle) },
                        onBookSelected = {
                            viewModel.selectBook(it)
                            navController.navigate(BookScreen.Detail.name)
                        }
                    )

                    BookContentType.LIST_AND_DETAIL -> Row() {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            BookMainScreen(
                                bookUiState = viewModel.bookUiState,
                                retryAction = { viewModel.getBooks(searchedBookTitle) },
                                onBookSelected = { viewModel.selectBook(it) },
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            BookDetailScreen(book = selectedBook)
                        }
                    }
                }
            }
            composable(route = BookScreen.Detail.name) {
                BookDetailScreen(selectedBook)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppBar(
    currentScreen: BookScreen,
    searchedBookTitle: String,
    selectedBookTitle: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = when (currentScreen) {
                    BookScreen.Search -> stringResource(id = R.string.app_name)
                    BookScreen.Main -> stringResource(R.string.main_screen_title, searchedBookTitle)
                    BookScreen.Detail -> selectedBookTitle
                },
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
    )
}