package com.ima.myhealthydiary.home

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ima.myhealthydiary.R
import com.ima.myhealthydiary.databinding.ActivityMainBinding
import com.ima.myhealthydiary.smartdiagnosis.SmartDiagnosisActivity

class MainActivity : AppCompatActivity() {
    private lateinit var bind : ActivityMainBinding
    private lateinit var adapter : MenuAdapter
    private lateinit var dataIcon : TypedArray
    private lateinit var dataTitle : Array<String>

    private var dataMenu = arrayListOf<MenuModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(bind.root)

        bind.ivProfile.clipToOutline = true
        bind.ivCovid.clipToOutline = true


        adapter = MenuAdapter(this)

        setData()
        addData()
        bind.rvMenu.layoutManager = GridLayoutManager(this, 2,RecyclerView.VERTICAL, false)
        bind.rvMenu.adapter = adapter
        adapter.setOnClick(object : MenuAdapter.OnItemClick {
            override fun OnItemClicked(position: Int) {
                when(position){
                    0 -> {
                        val intent = Intent(this@MainActivity, SmartDiagnosisActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

        })
    }

    private fun setData(){
        dataIcon = resources.obtainTypedArray(R.array.icon_menu)
        dataTitle = resources.getStringArray(R.array.title_menu)
    }

    private fun addData(){
        for (i in dataTitle.indices) {
            val menu = MenuModel(
                dataIcon.getResourceId(i, -1),
                dataTitle[i]
            )
            dataMenu.add(menu)
        }
        adapter.dataMenu = dataMenu
    }
}