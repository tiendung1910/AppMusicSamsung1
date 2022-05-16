package com.example.appmusicsamsung1.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.appmusicsamsung1.R
import com.example.appmusicsamsung1.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,"Welcome to register",Toast.LENGTH_LONG).show()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.LoginRedirect.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}