package com.ima.myhealthydiary.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.ima.myhealthydiary.databinding.ActivityRegisterBinding
import com.ima.myhealthydiary.home.MainActivity
import com.ima.myhealthydiary.utils.db.room.UserEntity
import com.ima.myhealthydiary.utils.db.room.UserRoomDatabase
import com.ima.myhealthydiary.utils.sharedpreference.Constants
import com.ima.myhealthydiary.utils.sharedpreference.PreferenceHelper
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var bind : ActivityRegisterBinding
    private lateinit var coroutine : CoroutineScope
    private lateinit var sharedpreference : PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(bind.root)

        coroutine = CoroutineScope(Job() + Dispatchers.IO)
        sharedpreference = PreferenceHelper(this)
        val userDao = UserRoomDatabase.getUserDatabase(this).userDao()

        bind.btnRegister.setOnClickListener{
            val email = bind.etEmail.text.toString()
            val password = bind.etPassword.text.toString()
            val confirmPass = bind.etPasswordConfirm.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (password == confirmPass) {
                    coroutine.launch {
                        userDao.insert(UserEntity(email, password, 1))
                    }
                    sharedpreference.putBoolean(Constants.LOGIN, true)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Konfirmasi Password Tidak Sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Lengkapi Form Terlebih Dulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        coroutine.cancel()
        super.onDestroy()
    }
}