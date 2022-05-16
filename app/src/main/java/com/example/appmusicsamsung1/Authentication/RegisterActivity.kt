package com.example.appmusicsamsung1.Authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.appmusicsamsung1.MainActivity
import com.example.appmusicsamsung1.R
import com.example.appmusicsamsung1.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(this,"Welcome to register",Toast.LENGTH_LONG).show()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //handle register
        val Register = binding.RegisterBtn
        Register.setOnClickListener {
            val email = binding.RegisterEmail.text.toString()
            val password = binding.RegisterPassword.text.toString()
            val repassword = binding.RegisterRepeatPassword.text.toString()
            handleRegister(email, password, repassword)
        }
        //redirect to Login
        binding.LoginRedirect.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun handleRegister(email:String, password:String, rpassword:String){
        if(email.isEmpty() || password.isEmpty() || rpassword.isEmpty()){
            Toast.makeText(this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_LONG).show()
            return
        }else{
            if(password == rpassword){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(!it.isSuccessful) return@addOnCompleteListener
                        Log.d("register","${it.result.user?.uid}")
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    .addOnFailureListener {
                        Log.d("register", "Đăng ký thất bại")
                    }
            }else{
                Toast.makeText(this,"Mật khẩu phải giống nhau",Toast.LENGTH_LONG).show()
                return
            }
        }
    }
}