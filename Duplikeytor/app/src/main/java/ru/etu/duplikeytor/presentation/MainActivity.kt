package ru.etu.duplikeytor.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.navigation.model.NavigationBarState
import ru.etu.duplikeytor.presentation.navigation.view.NavigationBar
import ru.etu.duplikeytor.presentation.uiKit.button.ButtonState
import ru.etu.duplikeytor.presentation.uiKit.button.UiKitButton
import ru.etu.duplikeytor.presentation.uiKit.theme.Theme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    // TODO use navBarBuilder
    private val navigationBarState = NavigationBarState(
        buttons = listOf(
            ButtonState.Icon.Navigation(
                icon = R.drawable.ic_key_white,
            ),
            ButtonState.Icon.Navigation(
                icon = R.drawable.ic_saved_white,
            ),
            ButtonState.Icon.Navigation(
                icon = R.drawable.ic_about_white,
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Box( // TODO Тоже костыль
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        val context = LocalContext.current
                        Box(
                            modifier = Modifier.padding(innerPadding)
                        )
                        {
                            UiKitButton(
                                modifier = Modifier,
                                button = ButtonState.Text(
                                    text = "Продолжить",
                                ),
                                onClick = { Toast.makeText(context, "Братуха привет", Toast.LENGTH_SHORT).show() }
                            )
                        }
                        NavigationBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            state = navigationBarState,
                            paddingValues = innerPadding, // TODO костыль
                        )
                    }
                }
            }
        }
    }
}