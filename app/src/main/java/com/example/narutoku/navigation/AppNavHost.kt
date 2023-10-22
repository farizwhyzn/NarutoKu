package com.example.narutoku.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.narutoku.ui.screen.list.CharacterListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.CharacterList.route
) {
    NavHost(navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.CharacterList.route) {
            CharacterListScreen(navController = navController)
        }
//        composable(route = Screen.CharacterDetail.route + "/{characterId}") {
//            CharacterDetailScreen(navController = navController)
//        }
//        composable(route = Screen.CharacterSearch.route) {
//            CharacterSearchScreen(navController = navController)
//        }
    }
}