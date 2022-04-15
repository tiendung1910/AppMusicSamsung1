package com.example.appmusicsamsung1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appmusicsamsung1.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection, MediaPlayer.OnCompletionListener {
    companion object {
        lateinit var musicListPA: ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying: Boolean = false
        var musicService: MusicService? = null
        lateinit var binding: ActivityPlayerBinding
        var repeat: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppMusicSamsung1)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)
        initializeLayout()
        binding.backBtn.setOnClickListener { finish() }
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
        binding.seekbarMusic.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                if(p2) musicService!!.mediaPlayer!!.seekTo(p1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
        binding.repeatBtn.setOnClickListener{
            if (!repeat) {
                repeat = true
                binding.repeatBtn.setColorFilter(ContextCompat.getColor(this,R.color.purple_500))
            }else{
                repeat = false
                binding.repeatBtn.setColorFilter(ContextCompat.getColor(this,R.color.black))
            }
        }
    }

    private fun setLayout() {
        Glide.with(this)
            .load(musicListPA[songPosition].imgUri)
            .apply(RequestOptions().placeholder(R.drawable.app_music_player_slash_screen).centerCrop())
            .into(binding.songImg)
        binding.songName.text = musicListPA[songPosition].title
        if (repeat) binding.repeatBtn.setColorFilter(ContextCompat.getColor(this,R.color.purple_500))
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
            musicService!!.showNotification(R.drawable.pause_icon)
            binding.durationSeekbar.text = formatDuration(musicService!!.mediaPlayer!!.currentPosition.toLong())
            binding.endSeekbar.text = formatDuration(musicService!!.mediaPlayer!!.duration.toLong())
            binding.seekbarMusic.progress = 0
            binding.seekbarMusic.max = musicService!!.mediaPlayer!!.duration
            musicService!!.mediaPlayer!!.setOnCompletionListener (this)
        }catch (e: Exception){ return }
    }



    private fun initializeLayout() {
        songPosition = intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")) {
            "MusicAdapter" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                setLayout()
//                createMediaPlayer()
            }
            "MainActivity" -> {
                musicListPA = ArrayList()
                musicListPA.addAll(MainActivity.MusicListMA)
                musicListPA.shuffle()
                setLayout()

            }
        }
    }

    private fun playMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.pause_icon)
        musicService!!.showNotification(R.drawable.pause_icon)
        isPlaying = true
        musicService!!.mediaPlayer!!.start()
    }

    private fun pauseMusic() {
        binding.playPauseBtnPA.setIconResource(R.drawable.play_icon)
        musicService!!.showNotification(R.drawable.play_icon)
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

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
        musicService!!.seekBarSetup()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }

    override fun onCompletion(p0: MediaPlayer?) {
        setPositionMusic(increment = true)
        createMediaPlayer()
        try {
            setLayout()
        }catch (e: java.lang.Exception) {
            return
        }
    }

}