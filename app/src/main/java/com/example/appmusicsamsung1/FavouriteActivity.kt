package com.example.appmusicsamsung1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appmusicsamsung1.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFavouriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolPink)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtnF.setOnClickListener { finish() }
    }
}