package com.ima.myhealthydiary.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ima.myhealthydiary.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    private lateinit var bind : ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFormBinding.inflate(LayoutInflater.from(this))
        setContentView(bind.root)
    }
}