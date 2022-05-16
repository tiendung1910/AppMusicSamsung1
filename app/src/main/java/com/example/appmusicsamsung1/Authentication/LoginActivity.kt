package com.example.appmusicsamsung1.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.appmusicsamsung1.MainActivity
import com.example.appmusicsamsung1.R
import com.example.appmusicsamsung1.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,"Welcome to Login",Toast.LENGTH_LONG).show()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //handle login
        val Login = binding.LoginBtn
        Login.setOnClickListener {
            val email = binding.LoginEmail.text.toString()
            val password = binding.LoginPassword.text.toString()
            handleLogin(email,password)
        }
        //redirect to register form
        binding.RegisterRedirect.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }

    private fun handleLogin(email:String, password: String){
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show()
            return
        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener
                    Toast.makeText(this,"Đăng nhập thành công",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MainActivity::class.java))
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Vui lòng kiểm tra lại thông tin đăng nhập",Toast.LENGTH_LONG).show()
                }
        }
    }
}