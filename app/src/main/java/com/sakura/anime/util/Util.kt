package com.sakura.anime.util

import android.content.Context
import com.sakura.anime.data.remote.parse.AnimeSource
import com.sakura.download.utils.decrypt
import java.util.Base64
import android.content.Intent
import android.content.Intent.*
import androidx.core.content.FileProvider.getUriForFile
import java.io.File

/**
 * 先Base64解码数据，然后再AES解密
 */
fun AnimeSource.decryptData(data: String, key: String, iv: String): String {
    val bytes = Base64.getDecoder().decode(data.toByteArray())
    val debytes = bytes.decrypt(key, iv)
    return debytes.decodeToString()
}

fun getVersionName(context: Context): String {
    return context.packageManager.getPackageInfo(context.packageName, 0).versionName
}

fun Context.installApk(file: File) {
    val intent = Intent(ACTION_VIEW)
    val authority = "$packageName.provider"
    val uri = getUriForFile(this, authority, file)
    intent.setDataAndType(uri, "application/vnd.android.package-archive")
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(intent)
}