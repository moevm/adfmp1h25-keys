package ru.etu.duplikeytor.presentation.shared.model

import ru.etu.duplikeytor.R

internal enum class KeyType(val title: String, val imageR: Int) {
    KWIKSET("Kwikset", R.drawable.kwikset),
    SCHLAGE("Schlage", R.drawable.schlage),
}