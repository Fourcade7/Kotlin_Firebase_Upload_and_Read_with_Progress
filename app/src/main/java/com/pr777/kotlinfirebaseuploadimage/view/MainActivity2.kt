package com.pr777.kotlinfirebaseuploadimage.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.pr777.kotlinfirebaseuploadimage.R
import com.pr777.kotlinfirebaseuploadimage.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentt=intent
        var name=intentt.getStringExtra("name")
        var url=intentt.getStringExtra("url")

        binding.apply {
            textviewopennew.text=name
            Glide.with(imageviewopennew).load(url).into(imageviewopennew)

        }

    }
}