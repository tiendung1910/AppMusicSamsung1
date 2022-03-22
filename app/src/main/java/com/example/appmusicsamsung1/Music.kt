package com.example.appmusicsamsung1

import java.sql.Time
import java.time.Duration
import java.util.concurrent.TimeUnit

data class Music(val id:String, val title:String, val album:String, val artist:String, val duration: Long=0,
    val path: String,val imgUri: String)

fun formatDuration(duration: Long) : String {
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val second = TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) - minutes *
            TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)
    return String.format("%2d:%2d",minutes,second)
}