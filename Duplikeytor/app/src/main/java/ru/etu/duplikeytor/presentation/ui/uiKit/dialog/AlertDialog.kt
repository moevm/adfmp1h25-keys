package ru.etu.duplikeytor.presentation.ui.uiKit.dialog

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.etu.duplikeytor.R


@Composable
internal fun ApproveDialog(
    title: String,
    contentText: String,
    onDismissRequest: (Boolean) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest(false) },
        title = { Text(title) },
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_trash_white),
                tint = MaterialTheme.colorScheme.onBackground,
                contentDescription = null,
            )
        },
        text = {
            Text(
                text = contentText,
                textAlign = TextAlign.Unspecified,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onDismissRequest(true) }
            ) {
                Text(
                    text = "Подтвердить",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest(false) }
            ) {
                Text(
                    text = "Отмена",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.errorContainer,
                )
            }
        }
    )
}