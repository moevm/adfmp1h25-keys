import ru.etu.duplikeytor.presentation.shared.model.KeyType

internal data class KeyChosenState(
    val imageUri: String,
    val title: String,
    val type: KeyType,
)