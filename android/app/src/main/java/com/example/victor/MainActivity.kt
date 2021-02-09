package com.example.victor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startProphecyProcess(view: View) {
        val prophecyActivityIntent = Intent(this, ProphecyActivity::class.java)
        startActivity(prophecyActivityIntent)
    }
}