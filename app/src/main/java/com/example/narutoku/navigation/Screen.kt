package com.example.narutoku.navigation

sealed class Screen(val route: String) {
    object CharacterList : Screen("character_list_screen")
    object CharacterDetail : Screen("character_detail_screen")
    object CharacterSearch : Screen("character_search_screen")
}
