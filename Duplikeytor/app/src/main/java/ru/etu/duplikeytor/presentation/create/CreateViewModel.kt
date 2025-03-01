package ru.etu.duplikeytor.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.choose.KeyChosenState
import ru.etu.duplikeytor.presentation.create.model.choose.KeyType
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

internal class CreateViewModel @Inject constructor() : ViewModel(), Screen {

    override var statusBarState = MutableStateFlow<StatusBarState>(
        StatusBarState.Title(
            title = "Создать ключ",
            requiredDisplay = true,
        )
    )
    override var navigationBarState = MutableStateFlow(
        NavigationBarState.build()
    )

    override val screenType: ScreenType = ScreenType.CREATE

    private val keys
        get () = getKeyTypes()

    private var keyChosen: KeyChosenState? = null
    private var keyScale: Float? = null


    private val _state: MutableStateFlow<CreateScreenState> =
        MutableStateFlow(CreateScreenState.Choose(keys = keys))
    val state: StateFlow<CreateScreenState> = _state

    init {
        viewModelScope.launch {
            _state.collect { state ->
                statusBarState.emit(
                    getStatusBarOnState(state)
                )
            }
        }
    }

    private fun getStatusBarOnState(state: CreateScreenState): StatusBarState =
        when (state) {
            is CreateScreenState.Choose -> {
                StatusBarState.Title(
                    title = "Создать ключ",
                    requiredDisplay = true,
                )
            }
            is CreateScreenState.Scale -> {
                StatusBarState.Title(
                    title = "Масштабирование " + (keyChosen?.title ?: ""),
                    requiredDisplay = true,
                    onBackClick = { viewModelScope.launch { returnToPreviousState() } }
                )
            }
            is CreateScreenState.Change -> {
                StatusBarState.Title(
                    title = "Создание " + (keyChosen?.title ?: ""),
                    requiredDisplay = true,
                    onBackClick = { viewModelScope.launch { returnToPreviousState() } }
                )
            }
        }

    private suspend fun returnToPreviousState() {
        val previousState: CreateScreenState = when (state.value) {
            is CreateScreenState.Choose -> { return }
            is CreateScreenState.Scale -> { CreateScreenState.Choose(keys = keys) }
            is CreateScreenState.Change -> {
                keyChosen?.let { key ->
                    CreateScreenState.Scale(
                        key = key,
                        initialScale = keyScale ?: 1f
                    )
                } ?: CreateScreenState.Choose(keys = keys)
            }
        }
        _state.emit(previousState)
    }

    internal fun onKeyChoose(key: KeyChosenState) {
        keyChosen = key
        viewModelScope.launch {
            _state.emit(
                CreateScreenState.Scale(
                    key = key,
                    initialScale = keyScale ?: 1f
                )
            )
        }
    }

    internal fun onKeyScale(scale: Float) {
        keyScale = scale
    }

    internal fun onKeyScaled() {
        viewModelScope.launch { _state.emit(CreateScreenState.Change()) }
    }

    private fun getKeyTypes() = listOf(
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
            title = "Kwikset",
            type = KeyType.KWIKSET,
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
            title = "SCHLAGE",
            type = KeyType.KWIKSET,
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
            title = "Дима",
            type = KeyType.KWIKSET,
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/70469206?v=4",
            title = "Kwikset.",
            type = KeyType.KWIKSET,
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90708652?v=4",
            title = "SCHLAGE.",
            type = KeyType.KWIKSET,
        ),
        KeyChosenState(
            imageUri = "https://avatars.githubusercontent.com/u/90792387?v=4",
            title = "Дима.",
            type = KeyType.KWIKSET,
        ),
    )
}