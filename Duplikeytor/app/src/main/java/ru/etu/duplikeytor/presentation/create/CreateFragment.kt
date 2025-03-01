package ru.etu.duplikeytor.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import ru.etu.duplikeytor.presentation.create.model.CreateEvent
import ru.etu.duplikeytor.presentation.create.model.CreateScreenState
import ru.etu.duplikeytor.presentation.create.view.choose.ChooseScreen
import ru.etu.duplikeytor.presentation.create.view.scale.ScaleScreen

@Composable
internal fun CreateFragment(
    viewModel: CreateViewModel,
    onCreate: () -> Unit = {},
    contentPadding: PaddingValues,
) {
    onCreate()
    val state by viewModel.state.collectAsState()

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
        is CreateScreenState.Change -> {
            Box(
                Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
        }
    }
}