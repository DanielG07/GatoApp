package com.danielg07.gato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun playButtonClick(view: View){
        var intent = Intent(this, PlayerSetup::class.java);
        startActivity(intent);
    }
}