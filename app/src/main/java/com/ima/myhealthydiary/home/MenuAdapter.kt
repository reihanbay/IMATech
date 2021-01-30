package com.ima.myhealthydiary.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ima.myhealthydiary.databinding.ItemGridMenuBinding

class MenuAdapter(private val context: Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    var dataMenu : ArrayList<MenuModel> = arrayListOf()
    private lateinit var onItemClick : OnItemClick

    fun setOnClick(OnItemClick: OnItemClick) {
        this.onItemClick = OnItemClick
    }

    interface OnItemClick {
        fun OnItemClicked(position: Int)
    }

    class MenuViewHolder(bind: ItemGridMenuBinding): RecyclerView.ViewHolder(bind.root){
        val icon = bind.ivIconMenu
        val title = bind.tvTitleMenu

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.MenuViewHolder {
        val view = ItemGridMenuBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val data = dataMenu[position]
        holder.icon.setImageResource(data.icon)
        holder.title.text = data.title
        holder.itemView.setOnClickListener{onItemClick.OnItemClicked(position)}
    }

    override fun getItemCount(): Int = dataMenu.size
}