package com.example.appmusicsamsung1

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusicsamsung1.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection {
    companion object {
        lateinit var musicListPA : ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying: Boolean = false
        var musicService: MusicService? = null
    }

    private lateinit var binding:ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppMusicSamsung1)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        initializeLayout()
        binding.playPauseBtnPA.setOnClickListener {
            if (isPlaying) {
                pauseMusic()
            }else {
                playMusic()
            }
        }
        binding.previousBtnPA.setOnClickListener{
            prevSong(false)
        }
        binding.nextBtnPA.setOnClickListener {
            prevSong(true)
        }

//        songPosition = intent.getIntExtra("index",0)
//        when(intent.getStringExtra("class")) {
//            "MusicAdapter" -> {
//                musicListPA = ArrayList()
//                musicListPA.addAll(MainActivity.MusicListMA)
//                if (mediaPlayer == null) mediaPlayer = MediaPlayer()
//                mediaPlayer!!.reset()
//                mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
//                mediaPlayer!!.prepare()
//                mediaPlayer!!.start()
//
//            }
//        }
    }

    private fun setLayout() {
        Glide.with(this)
            .load(musicListPA[songPosition].imgUri)
            .apply(RequestOptions().placeholder(R.drawable.app_music_player_slash_screen).centerCrop())
            .into(binding.songImg)
        binding.songName.text = musicListPA[songPosition].title
    }

    private fun createMediaPlayer(){
        try {
            if(musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlaying = true
            binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)


        }catch (e: Exception){ return }
    }

    private fun initializeLayout() {
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()
                createMediaPlayer()
            }
//            "MainActivity" -> {
//                musicListPA = ArrayList()
//                musicListPA.addAll(MainActivity.MusicListMA)
//                musicListPA.shuffle()
//                setLayout()
//
//            }
        }
    }

    private fun playMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
        isPlaying = false
        musicService!!.mediaPlayer!!.pause()
    }

    private fun prevSong(increment: Boolean) {
        if (increment) {
            setPositionMusic(increment=true)
            setLayout()
            createMediaPlayer()
        } else {
            setPositionMusic(increment=false)
            setLayout()
            createMediaPlayer()
        }
    }

    private fun setPositionMusic(increment: Boolean) {
        if(increment){
            if(musicListPA.size-1== songPosition)
                songPosition = 0
            else ++songPosition
        } else {
            if (songPosition == 0)
                songPosition = musicListPA.size-1
            else --songPosition
        }
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null

    }
}