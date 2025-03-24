package ru.etu.duplikeytor.presentation.create

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.view.choose.ChooseScreen
import ru.etu.duplikeytor.presentation.create.view.create.CreateScreen
import ru.etu.duplikeytor.presentation.create.view.save.SaveScreen
import ru.etu.duplikeytor.presentation.create.view.scale.ScaleScreen
import ru.etu.duplikeytor.presentation.holder.model.AppEvent

@Composable
internal fun CreateFragment(
    viewModel: CreateViewModel,
    contentPadding: PaddingValues,
    onBackClick: () -> Boolean,
    onBackFailure: () -> Unit,
    onCreate: () -> Unit = {},
    processAppEvent: (AppEvent) -> Unit,
) {
    onCreate()

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
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
                        is CreateEvent.KeySave -> {
                            viewModel.onSaveKey { title, id ->
                                processAppEvent(AppEvent.Archive.KeySaved(id))
                                showSaveToast(
                                    context = context,
                                    text = createSaveText(title),
                                )
                            }
                        }
                        is CreateEvent.KeyTitleChange -> viewModel.keyTitleChange(event.title)
                        is CreateEvent.Share -> viewModel.onKeyShare(event.context)
                        is CreateEvent.SetKeyImage -> viewModel.onSetKeyImage(event.uri)
                        is CreateEvent.DeleteKeyPhoto -> viewModel.onDeleteKeyPhoto()
                        else -> Unit
                    }
                }
            )
        }
    }
}

private fun createSaveText(title: String) = "Слепок ключа ${title} сохранен"

private fun showSaveToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}