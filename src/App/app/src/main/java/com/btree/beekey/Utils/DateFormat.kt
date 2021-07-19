package com.btree.beekey.Utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class DateFormat {
    companion object {
        fun Date.dateformat(): String {
            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return formatter.format(this)
        }
    }
}