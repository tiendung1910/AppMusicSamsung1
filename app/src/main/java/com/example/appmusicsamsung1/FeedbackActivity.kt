package com.example.appmusicsamsung1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.appmusicsamsung1.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {
    lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Feedback"
        val feedbackMsg = binding.contentFB.text.toString()+"\n"+binding.emailFB.text.toString()
        val subject = binding.topicFB.text.toString()
        binding.sendFBBtn.setOnClickListener {
            Toast.makeText(this,feedbackMsg,Toast.LENGTH_LONG).show()
        }
    }
}