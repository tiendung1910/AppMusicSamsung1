package com.example.appmusicsamsung1

import java.sql.Time
import java.time.Duration
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

data class Music(val id:String, val title:String, val album:String, val artist:String, val duration: Long=0,
    val path: String,val imgUri: String)

class Playlist {
    lateinit var name: String
    lateinit var playlist: ArrayList<Music>
    lateinit var createBy: String
    lateinit var createOn: String
}

class MusicPlaylist {
    var ref: ArrayList<Playlist> = ArrayList()
}

fun formatDuration(duration: Long) : String {
    val minutes = TimeUnit.MINUTES.convert(duration,TimeUnit.MILLISECONDS)
    val second = TimeUnit.SECONDS.convert(duration,TimeUnit.MILLISECONDS) - minutes *
            TimeUnit.SECONDS.convert(1,TimeUnit.MINUTES)
    return String.format("%2d:%2d",minutes,second)
}

fun setPositionMusic(increment: Boolean) {
    if (!PlayerActivity.repeat) {
        if(increment){
            if(PlayerActivity.musicListPA.size-1== PlayerActivity.songPosition)
                PlayerActivity.songPosition = 0
            else ++PlayerActivity.songPosition
        } else {
            if (PlayerActivity.songPosition == 0)
                PlayerActivity.songPosition = PlayerActivity.musicListPA.size-1
            else --PlayerActivity.songPosition
        }
    }
}
fun exitApplication() {
    if (PlayerActivity.musicService != null) {
        PlayerActivity.musicService!!.audioManager.abandonAudioFocus(PlayerActivity.musicService)
        PlayerActivity.musicService!!.stopForeground(true)
        PlayerActivity.musicService!!.mediaPlayer!!.release()
        PlayerActivity.musicService = null

    }
    exitProcess(1)
}

fun favouriteChecker(id: String): Int {
    PlayerActivity.isFavourite = false
    FavouriteActivity.favouriteSongs.forEachIndexed { index, music ->
        if(id == music.id) {
            PlayerActivity.isFavourite = true
            return index
        }
    }
    return -1
}