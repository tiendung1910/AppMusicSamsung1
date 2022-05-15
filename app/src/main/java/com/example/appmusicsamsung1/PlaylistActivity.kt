package com.example.appmusicsamsung1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appmusicsamsung1.databinding.ActivityPlaylistBinding
import com.example.appmusicsamsung1.databinding.AddPlaylistDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlaylistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistBinding
    private lateinit var adapter: PlaylistViewAdapter

    companion object {
        var musicPlaylist: MusicPlaylist = MusicPlaylist()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_AppMusicSamsung1)
        binding = ActivityPlaylistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.playlistRV.setHasFixedSize(true)
        binding.playlistRV.setItemViewCacheSize(13)
        binding.playlistRV.layoutManager = GridLayoutManager(this,2)

        adapter = PlaylistViewAdapter(this, playlistList = musicPlaylist.ref)
        binding.playlistRV.adapter = adapter

        binding.backBtnPl.setOnClickListener { finish() }
        binding.addPlaylistBtn.setOnClickListener { customAlertDialog() }
    }
    private fun customAlertDialog(){
        val customDialog = LayoutInflater.from(this).inflate(R.layout.add_playlist_dialog,binding.root,false)
        val binder = AddPlaylistDialogBinding.bind(customDialog)
        val builder = MaterialAlertDialogBuilder(this)
        builder.setView(customDialog)
            .setTitle("Playlist details")
            .setPositiveButton("add") {dialog,_ ->
                val playlistName = binder.playlistName.text
                val createBy = binder.yourNameTi.text
                if (playlistName != null && createBy != null) {
                    if(playlistName.isNotEmpty() && createBy.isNotEmpty()) {
                        addPlaylist(playlistName.toString(),createBy.toString())
                    }
                }

                dialog.dismiss()
            }.show()

    }

    private fun addPlaylist(name: String, createBy: String) {
        var playlistExists = false
        for(i in musicPlaylist.ref) {
            if(name.equals(i.name)) {
                playlistExists = true
                break
            }
        }
        if(playlistExists) Toast.makeText(this,"playlist exist",Toast.LENGTH_LONG).show()
        else{
            val tempPlaylist = Playlist()
            tempPlaylist.name = name
            tempPlaylist.playlist = ArrayList()
            tempPlaylist.createBy = createBy
            val calendar = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MM yyyy",Locale.ENGLISH)
            tempPlaylist.createOn = sdf.format(calendar)
            musicPlaylist.ref.add(tempPlaylist)
            adapter.refreshPlaylist()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}