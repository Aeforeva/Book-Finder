package com.example.bookfinder.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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

enum class BookScreen() {
    Search, // Starter screen
    Main, // Search result scree
    Detail // Detail book info screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookApp(modifier: Modifier = Modifier) {

    val viewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookScreen.valueOf(
        backStackEntry?.destination?.route ?: BookScreen.Search.name
    )
    var searchedBookTitle by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            BookAppBar(
                currentScreen = currentScreen,
                searchedBookTitle = searchedBookTitle,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BookScreen.Search.name,
            modifier = modifier.padding(innerPadding)
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
                BookMainScreen(
                    bookUiState = viewModel.bookUiState,
                    onBookSelected = {}
                )
            }
            composable(route = BookScreen.Detail.name) {
                BookDetailScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppBar(
    currentScreen: BookScreen,
    searchedBookTitle: String,
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
                    BookScreen.Detail -> searchedBookTitle
                },
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        modifier = modifier,
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