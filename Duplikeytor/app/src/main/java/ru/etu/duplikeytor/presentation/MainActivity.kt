package ru.etu.duplikeytor.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.etu.duplikeytor.R
import ru.etu.duplikeytor.presentation.navigation.model.NavigationBarState
import ru.etu.duplikeytor.presentation.navigation.model.NavigationButtonState
import ru.etu.duplikeytor.presentation.navigation.view.NavigationBar
import ru.etu.duplikeytor.presentation.uiKit.theme.Theme

class MainActivity : ComponentActivity() {

    // TODO provide ViewModel

    // TODO use navBarBuilder
    private val navigationBarState = NavigationBarState(
        buttons = listOf(
            NavigationButtonState(
                iconRes = R.drawable.ic_key_white,
                onClick = {},
            ),
            NavigationButtonState(
                iconRes = R.drawable.ic_saved_white,
                onClick = {},
            ),
            NavigationButtonState(
                iconRes = R.drawable.ic_about_white,
                onClick = {},
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