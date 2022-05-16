package com.example.appmusicsamsung1.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.appmusicsamsung1.R
import com.example.appmusicsamsung1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,"Welcome to Login",Toast.LENGTH_LONG).show()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RegisterRedirect.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
}