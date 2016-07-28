package ceg.avtechlabs.brew.commons.utilities

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by adhithyan-3592 on 23/07/16.
 */

fun getStorageDir() : File? {
    var dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Brew")
    when { !dir.exists() -> dir.mkdirs() }
    return dir
}

fun ImageView.save(fileName: String) : Boolean{
    val drawable = this.drawable
    val bitmap = (drawable as BitmapDrawable).bitmap
    var stream = ByteArrayOutputStream()

    bitmap?.compress(Bitmap.CompressFormat.JPEG, 80, stream)
    val bytes = stream.toByteArray()

    var path = getStorageDir()?.absolutePath ?: null
    var fullFileName: String? = null
    when { path is String -> fullFileName = path + File.separator + fileName + ".jpg" }

    try{
        val out = FileOutputStream(fullFileName)
        out.write(bytes)
        out.close()
    } catch (e: Exception) {
        Log.d("error", e.message)
        return false
    }

    return true
}