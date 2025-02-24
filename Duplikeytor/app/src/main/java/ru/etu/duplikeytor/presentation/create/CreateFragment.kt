package ru.etu.duplikeytor.presentation.create

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                state = state as CreateScreenState.Scale,
                onEvent = {} // TODO next issue
            )
        }
    }
}