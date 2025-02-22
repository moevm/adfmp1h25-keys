package ru.etu.duplikeytor.presentation.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.etu.duplikeytor.presentation.ui.uiKit.theme.Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                MainScreen(
                    onScreenChanged = viewModel::onScreenChanged,
                    onLifecycleEvent = viewModel::onLifecycleEvent,
                )
            }
        }
    }
}