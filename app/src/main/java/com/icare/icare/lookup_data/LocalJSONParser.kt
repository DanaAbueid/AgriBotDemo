package com.icare.icare.lookup_data

import android.content.Context
import java.io.IOException

class LocalJSONParser {

    companion object {
        fun inputStreamToString(context: Context, fileName: String): String {
            try {
                val jsonString = context.assets.open(fileName)
                    .bufferedReader()
                    .use { it.readText() }
                return jsonString.trim()
            } catch (e: IOException) {
                return ""
            }
        }
    }
}
