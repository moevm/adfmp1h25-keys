package ru.etu.duplikeytor.presentation.create

import KeyChosenState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.KeyType
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
    override fun onBackClick(): Boolean =
        if (!_interfaceVisibleState.value) {
            changeInterfaceVisibility()
            true
        } else {
            returnToPreviousState()
        }

    private val keys = getKeyTypes()

    private var keyChosen: KeyChosenState? = null
    private var keyScale: Float = 1f

    private val _interfaceVisibleState = MutableStateFlow(true)
    val interfaceVisibleState = _interfaceVisibleState

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
            is CreateScreenState.Create -> {
                StatusBarState.Title(
                    title = "Создание " + (keyChosen?.title ?: ""),
                    requiredDisplay = true,
                    onBackClick = { viewModelScope.launch { returnToPreviousState() } }
                )
            }
            is CreateScreenState.Save -> {
                StatusBarState.Title(
                    title = "Назовите ключ",
                    requiredDisplay = true,
                    onBackClick = { viewModelScope.launch { returnToPreviousState() } }
                )
            }
        }

    private fun returnToPreviousState(): Boolean {
        val previousState: CreateScreenState = when (state.value) {
            is CreateScreenState.Choose -> {
                resetKeyInfo()
                return false
            }
            is CreateScreenState.Scale -> {
                resetKeyInfo()
                CreateScreenState.Choose(keys = keys)
            }
            is CreateScreenState.Create -> {
                keyChosen?.let { key ->
                    CreateScreenState.Scale(
                        key = key,
                        initialScale = keyScale
                    )
                } ?: CreateScreenState.Choose(keys = keys)
            }
            is CreateScreenState.Save -> {
                keyChosen?.let { key ->
                    CreateScreenState.Create(
                        key = key,
                        scale = keyScale
                    )
                } ?: CreateScreenState.Choose(keys = keys)
            }
        }

        changeState(previousState)
        return true
    }

    private fun changeState(targetState: CreateScreenState) =
        viewModelScope.launch { _state.emit(targetState) }

    internal fun onKeyChoose(key: KeyChosenState) {
        keyChosen = key
        changeState(
            CreateScreenState.Scale(
                key = key,
                initialScale = keyScale
            )
        )
    }

    internal fun onKeyScale(scale: Float) {
        keyScale = scale
    }

    internal fun onKeyScaled() {
        val key: KeyChosenState = keyChosen ?: return
        changeState(
            CreateScreenState.Create(
                key = key,
                scale = keyScale
            )
        )
    }

    internal fun onKeyCreated() {
        val key: KeyChosenState = keyChosen ?: return
        val keyConfig = null // TODO
        changeState(
            CreateScreenState.Save(
                key = key,
                scale = keyScale,
                keyConfig = keyConfig,
            )
        )
    }

    internal fun onSaveKey() {
        // TODO save key (keyConfig, and title) in DB
        resetKeyInfo()
        changeState(CreateScreenState.Choose(keys = keys))
    }

    private fun resetKeyInfo() {
        keyChosen = null
        keyScale = 1f
        // TODO keyConfig = null
    }

    fun changeInterfaceVisibility() {
        _interfaceVisibleState.value = !_interfaceVisibleState.value
        viewModelScope.launch {
            statusBarState.emit(when(val currentStatusBar = statusBarState.value) {
                is StatusBarState.Title ->
                    currentStatusBar.copy(requiredDisplay = _interfaceVisibleState.value)
                is StatusBarState.Empty ->
                    currentStatusBar.copy(requiredDisplay = _interfaceVisibleState.value)
            })
            navigationBarState.emit(navigationBarState.value.copy(requiredDisplay = _interfaceVisibleState.value))
        }
    }

    private fun getKeyTypes() = listOf(
        KeyChosenState(
            imageUri = "https://images.kwikset.com/is/image/Kwikset/05991-mk-any_c3?wid=600&qlt=90&resMode=sharp",
            title = "Kwikset",
            type = KeyType.KWIKSET,
        ),
        KeyChosenState(
            imageUri = "https://cdn.mscdirect.com/global/images/ProductImages/1716860-21.jpg",
            title = "SCHLAGE",
            type = KeyType.SCHLAGE,
        ),
    )
}