package com.example.appmusicsamsung1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.system.exitProcess

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action) {
            ApplicationClass.PREVIOUS -> prevNextSong(false, context!!)
            ApplicationClass.PLAY -> if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
            ApplicationClass.NEXT -> prevNextSong(true, context!!)
            ApplicationClass.EXIT -> {
                PlayerActivity.musicService!!.stopForeground(true)
                PlayerActivity.musicService!!.mediaPlayer!!.release()
                PlayerActivity.musicService = null
                exitProcess(1)
            }
        }
    }

    private fun playMusic() {
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon)
        PlayerActivity.binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
    }

    private fun pauseMusic() {
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.play_icon)
        PlayerActivity.binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
    }

    private fun prevNextSong(increment: Boolean, context: Context) {
        setPositionMusic(increment = increment)
        PlayerActivity.musicService!!.createMediaPlayer()
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].imgUri)
            .apply(RequestOptions().placeholder(R.drawable.app_music_player_slash_screen).centerCrop())
            .into(PlayerActivity.binding.songImg)

        PlayerActivity.binding.songName.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
        playMusic()
    }


}