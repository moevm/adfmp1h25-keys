package ru.etu.duplikeytor.domain.repository

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class ImageRepository  @Inject constructor(
    private val context: Context,
) {
    fun copyUriToLocalStorage(uri: Uri): Uri? {
        return try {
            val fileName = "image_${UUID.randomUUID()}.jpg"
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun deletePhotoByUri(uri: Uri?) {
        uri?.let {
            val file = File(it.path)
            if (file.exists()) {
                file.delete()
            }
        }
    }
}