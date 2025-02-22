package ru.etu.duplikeytor.presentation.about.model

internal data class AboutScreenState(
    val developers: List<DeveloperState>,
    val department: DepartmentState,
)