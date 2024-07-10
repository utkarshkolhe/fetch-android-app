package com.example.fetchlistapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GreetScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_greet_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Set animation for slpash screen and transition
        val imageView: ImageView = findViewById(R.id.imageView)
        val intent = Intent(this@GreetScreen, ListScreen::class.java)
        animateImageView(imageView,intent)

    }

    //Creates zoom in then zoom out effect for logo
    private fun animateImageView(imageView: ImageView, intent: Intent) {
        // Scale up animation
        val scaleXUp = ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.5f)
        val scaleYUp = ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.5f)

        // Scale down animation
        val scaleXDown = ObjectAnimator.ofFloat(imageView, "scaleX", 1.5f, 0.8f)
        val scaleYDown = ObjectAnimator.ofFloat(imageView, "scaleY", 1.5f, 0.8f)

        // Set the duration for each animator
        scaleXUp.duration = 500
        scaleYUp.duration = 500
        scaleXDown.duration = 500
        scaleYDown.duration = 500

        // Create an AnimatorSet to play the animations sequentially
        val animatorSet = AnimatorSet()
        animatorSet.play(scaleXUp).with(scaleYUp)
        animatorSet.play(scaleXDown).with(scaleYDown).after(scaleXUp)

        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                startActivity(intent)
                // Finish splash screen
                finish()
            }
        })
        // Start the animation
        animatorSet.start()
    }
}