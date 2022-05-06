package com.zhadko.mycameraapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhadko.mycameraapp.databinding.ActivityMainBinding
import com.zhadko.mycameraapp.ui.photocamera.PhotoCameraFragment

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, PhotoCameraFragment())
            .commit()
    }

}