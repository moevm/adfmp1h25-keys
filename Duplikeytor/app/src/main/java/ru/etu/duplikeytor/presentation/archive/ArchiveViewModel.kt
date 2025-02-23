package ru.etu.duplikeytor.presentation.archive

import androidx.lifecycle.ViewModel
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class ArchiveViewModel @Inject constructor() : ViewModel(), Screen {
    override var statusBarState: StatusBarState = StatusBarState.Title(
        title = "Архив ключей",
        requiredDisplay = true,
    )
    override var navigationBarState: NavigationBarState = NavigationBarState.build()

    override val screenType: ScreenType = ScreenType.ARCHIVE

    internal fun changeStatusBarTitle(title: String) {
        statusBarState = StatusBarState.Title(
            title = title,
            requiredDisplay = statusBarState.requiredDisplay,
        )
    }
}