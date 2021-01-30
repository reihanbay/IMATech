package com.ima.myhealthydiary.smartdiagnosis

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ima.myhealthydiary.databinding.ItemChoiceAnswerBinding
import com.ima.myhealthydiary.databinding.ItemGridMenuBinding

class ChoiceAdapter(private val context: Context) : RecyclerView.Adapter<ChoiceAdapter.MenuViewHolder>() {

    var dataMenu : List<String> = listOf()
    private lateinit var onItemClick : OnItemClick

    fun setOnClick(OnItemClick: OnItemClick) {
        this.onItemClick = OnItemClick
    }

    interface OnItemClick {
        fun OnItemClicked(message: String)
    }

    class MenuViewHolder(bind: ItemChoiceAnswerBinding): RecyclerView.ViewHolder(bind.root){
        val tv = bind.tvChoice

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoiceAdapter.MenuViewHolder {
        val view = ItemChoiceAnswerBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val data = dataMenu[position]
        holder.tv.text = data
        holder.itemView.setOnClickListener{onItemClick.OnItemClicked(data)}
    }

    override fun getItemCount(): Int = dataMenu.size
}