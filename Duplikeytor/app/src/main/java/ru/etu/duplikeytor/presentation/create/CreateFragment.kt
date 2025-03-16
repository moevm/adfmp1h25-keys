package ru.etu.duplikeytor.presentation.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.view.choose.ChooseScreen
import ru.etu.duplikeytor.presentation.create.view.create.CreateScreen
import ru.etu.duplikeytor.presentation.create.view.save.SaveScreen
import ru.etu.duplikeytor.presentation.create.view.scale.ScaleScreen

@Composable
internal fun CreateFragment(
    viewModel: CreateViewModel,
    contentPadding: PaddingValues,
    onBackClick: () -> Boolean,
    onBackFailure: () -> Unit,
    onCreate: () -> Unit = {},
) {
    onCreate()

    val state by viewModel.state.collectAsState()
    val interfaceVisibilityState by viewModel.interfaceVisibleState.collectAsState()

    BackHandler {
        if (!onBackClick()) {
            onBackFailure()
        }
    }

    when(state) {
        is CreateScreenState.Choose -> {
            ChooseScreen(
                modifier = Modifier.padding(contentPadding),
                state = state as CreateScreenState.Choose,
                onEvent = { chooseEvent ->
                    viewModel.onKeyChoose(chooseEvent.chosenKey)
                }
            )
        }
        is CreateScreenState.Scale -> {
            ScaleScreen(
                modifier = Modifier.padding(contentPadding),
                state = (state as CreateScreenState.Scale),
                onEvent = { event ->
                    when(event) {
                        is CreateEvent.KeyScale -> viewModel.onKeyScale(event.scale)
                        is CreateEvent.KeyScaled -> viewModel.onKeyScaled()
                        else -> Unit
                    }
                }
            )
        }
        is CreateScreenState.Create -> {
            CreateScreen(
                modifier = Modifier.padding(contentPadding),
                state = (state as CreateScreenState.Create),
                interfaceVisibleState = interfaceVisibilityState,
                onEvent = { event ->
                    when(event) {
                        is CreateEvent.KeyCreated -> {
                            viewModel.onKeyCreated()
                        }
                        is CreateEvent.InterfaceVisibleChange -> {
                            viewModel.changeInterfaceVisibility()
                        }
                        is CreateEvent.KeyCreateChanged -> {
                            viewModel.changeConfig(event.pin, event.deep)
                        }
                        else -> Unit
                    }
                },
            )
        }
        is CreateScreenState.Save -> {
            SaveScreen(
                modifier = Modifier.padding(contentPadding),
                state = (state as CreateScreenState.Save),
                onEvent = { event ->
                    when(event) {
                        is CreateEvent.KeySave -> viewModel.onSaveKey()
                        is CreateEvent.KeyTitleChange -> viewModel.keyTitleChange(event.title)
                        is CreateEvent.Share -> viewModel.onKeyShare(event.context)
                        else -> Unit
                    }
                }
            )
        }
    }
}