package ru.etu.duplikeytor.presentation.navigation.model

enum class ScreenType(val route: String) {
    CREATE("CREATE"),
    ARCHIVE("ARCHIVE"),
    ABOUT("ABOUT");

    companion object {
        val main = CREATE
    }
}