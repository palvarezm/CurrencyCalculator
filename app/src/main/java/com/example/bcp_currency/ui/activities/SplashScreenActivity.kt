package com.example.bcp_currency.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.bcp_currency.R

class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_TIME_DURATION:Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, CurrencyCalculatorActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_DURATION)
    }
}