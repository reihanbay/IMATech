package com.ima.myhealthydiary.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import com.ima.myhealthydiary.home.MainActivity
import com.ima.myhealthydiary.R
import com.ima.myhealthydiary.databinding.ActivityLoginBinding
import com.ima.myhealthydiary.register.RegisterActivity
import com.ima.myhealthydiary.utils.db.room.UserRoomDatabase
import com.ima.myhealthydiary.utils.sharedpreference.Constants
import com.ima.myhealthydiary.utils.sharedpreference.PreferenceHelper
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var bind : ActivityLoginBinding
    private lateinit var coroutine: CoroutineScope
    private lateinit var sharedPreference: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(bind.root)
        sharedPreference = PreferenceHelper(this)
        coroutine = CoroutineScope(Job() + Dispatchers.IO)
        bind.tvShowHide.setOnClickListener(this)
        bind.btnLogin.setOnClickListener(this)

    }

    private fun loginUser() {
        coroutine.launch {
            val userDao = UserRoomDatabase.getUserDatabase(this@LoginActivity).userDao()
            val email = bind.etEmail.text.toString()
            val password = bind.etPassword.text.toString()
            var data = userDao.getUser(email, password)
            val intent : Intent
            intent = if (data == null) {
                Intent(this@LoginActivity, RegisterActivity::class.java)
            } else {
                sharedPreference.putBoolean(Constants.LOGIN, true)
                Intent(this@LoginActivity, MainActivity::class.java)
            }
            startActivity(intent)

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_show_hide -> {
                if (bind.tvShowHide.text.toString() == "Tampilkan") {
                    bind.etPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    bind.tvShowHide.text = "Sembunyikan"
                } else {
                    bind.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    bind.tvShowHide.text = "Tampilkan"
                }
            }
            R.id.btn_login -> {
                loginUser()
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        sharedPreference = PreferenceHelper(this)
        val loginSession = sharedPreference.getBoolean(Constants.LOGIN)
        if (loginSession == true) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        coroutine.cancel()
    }
}