package ru.etu.duplikeytor.presentation.create

import KeyChosenState
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.etu.duplikeytor.domain.models.Key
import ru.etu.duplikeytor.domain.repository.KeyRepository
import ru.etu.duplikeytor.domain.usecases.ShareUsecase
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.model.config.KeyConfig
import ru.etu.duplikeytor.presentation.holder.model.AppEvent
import ru.etu.duplikeytor.presentation.holder.model.navigation.NavigationBarState
import ru.etu.duplikeytor.presentation.holder.model.navigation.ScreenType
import ru.etu.duplikeytor.presentation.holder.model.status.StatusBarState
import ru.etu.duplikeytor.presentation.shared.model.KeyType
import ru.etu.duplikeytor.presentation.shared.model.Screen
import javax.inject.Inject

@HiltViewModel
internal class CreateViewModel @Inject constructor(
    private val shareUsecase: ShareUsecase,
    private val keyRepository: KeyRepository,
) : ViewModel(), Screen {

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

    override fun notifyResolveEvent(event: AppEvent) {
        if (event !is AppEvent.Create) return
    }

    private val keys = getKeyTypes()

    private var keyChosen: KeyChosenState? = null
    private var keyScale: Float = 1f
    private var keyConfig: KeyConfig? = null
    private var keyTitle: String? = null
    private var keyId: Long = 0

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
                val (key, config) = keyChosen to keyConfig
                if (key != null && config != null) {
                    CreateScreenState.Create(
                        key = key,
                        scale = keyScale,
                        keyConfig = config,
                    )
                } else {
                    CreateScreenState.Choose(keys = keys)
                }
            }
        }

        changeState(previousState)
        return true
    }

    private fun changeState(targetState: CreateScreenState) =
        viewModelScope.launch { _state.emit(targetState) }

    internal fun onKeyChoose(key: KeyChosenState) {
        keyChosen = key
        keyConfig = when(key.type) {
            KeyType.KWIKSET -> {
                KeyConfig.Kwikset.init
            }
            else -> {
                null // TODO
            }
        }
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
        val config: KeyConfig = keyConfig ?: return
        changeState(
            CreateScreenState.Create(
                key = key,
                scale = keyScale,
                keyConfig = config,
            )
        )
    }

    internal fun changeConfig(pinNumber: Int, deep: Int) {
        keyConfig = when (keyConfig) {
            is KeyConfig.Kwikset -> {
                (keyConfig as KeyConfig.Kwikset).copy(
                    pins = (keyConfig as KeyConfig.Kwikset).pins.mapIndexed { index, value ->
                        if (index == pinNumber - 1) deep else value
                    }
                )
            }
            else -> {
                // TODO schalge, null
                keyConfig
            }
        }
    }

    internal fun onKeyCreated() {
        val key: KeyChosenState = keyChosen ?: return
        val keyConfig = keyConfig ?: return
        changeState(
            CreateScreenState.Save(
                key = key,
                scale = keyScale,
                keyConfig = keyConfig,
            )
        )
    }

    internal fun onSaveKey(onSuccessSave: (Long) -> Unit) {
        saveKeyIntoRepository(
            keyName = keyTitle ?: keyChosen?.type?.toString() ?: "No name",
            keyChose = keyChosen,
            keyConfig = keyConfig,
            keyId = keyId,
            onSuccessSave = onSuccessSave
        )
        resetKeyInfo()
        changeState(CreateScreenState.Choose(keys = keys))
    }

    internal fun onKeyShare(context: Context) {
        val keyType = keyChosen!!.type.toString()
        val keyName = keyChosen!!.title
        val pinsInfo = keyConfig!!.pins.toString()

        shareUsecase.share(
            context,
            title = "Отправить информацию о ключе",
            message = """
                Ключ: $keyName
                Тип ключа: $keyType
                Параметры: $pinsInfo
            """.trimIndent()
        )
    }


    internal fun onKeyEditIntent(id: Long) {
        viewModelScope.launch {
            val key = keyRepository.getKey(id)
            val keyEditConfig = when(KeyType.valueOf(key.type)) {
                KeyType.KWIKSET -> {
                    KeyConfig.Kwikset(
                        pins = key.pins
                    )
                }
                KeyType.SCHLAGE -> {
                    KeyConfig.Schlage(
                        pins = key.pins
                    )
                }
            }

            keyChosen = KeyChosenState(
                imageUri = key.type,
                title = key.name,
                type = KeyType.valueOf(key.type),
            )
            keyScale = key.scale
            keyConfig = keyEditConfig
            keyTitle = key.name
            keyId = key.id

            changeState(
                CreateScreenState.Save(
                    key = KeyChosenState(
                        imageUri = key.type,
                        title = key.name,
                        type = KeyType.valueOf(key.type),
                    ),
                    scale = keyScale,
                    keyConfig = keyEditConfig,
                )
            )
        }
    }

    private fun saveKeyIntoRepository(
        keyName: String,
        keyChose: KeyChosenState?,
        keyConfig: KeyConfig?,
        keyId: Long,
        onSuccessSave: (Long) -> Unit,
    ) {
        keyChose ?: return
        keyConfig ?: return

        val key = Key(
            id = keyId,
            name = keyName,
            scale = keyScale,
            pins = keyConfig.pins,
            type = keyChose.type.toString(),
        )

        viewModelScope.launch {
            runCatching {
                keyRepository.updateKey(key)
            }.onSuccess {
                onSuccessSave(if (it == -1L) key.id else it)
            }
        }
    }


    internal fun keyTitleChange(title: String) {
        keyTitle = title
    }

    private fun resetKeyInfo() {
        keyChosen = null
        keyScale = 1f
        keyConfig = null
        keyTitle = ""
        keyId = 0
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