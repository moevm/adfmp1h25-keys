package ru.etu.duplikeytor.domain.usecases

import android.content.Context
import android.content.Intent

class ShareUsecase {
    fun share(context: Context, title: String, message: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val shareIntent = Intent.createChooser(sendIntent, title)
        context.startActivity(shareIntent)
    }
}