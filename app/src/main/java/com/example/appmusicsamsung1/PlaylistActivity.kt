package com.example.appmusicsamsung1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appmusicsamsung1.databinding.ActivityPlaylistBinding

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppMusicSamsung1)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_playlist)
    }
}