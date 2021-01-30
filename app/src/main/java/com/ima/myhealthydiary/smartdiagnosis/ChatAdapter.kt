package com.ima.myhealthydiary.smartdiagnosis

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ima.myhealthydiary.databinding.MessageLayoutBinding

class ChatAdapter(val context: Context) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    var data = arrayListOf<ChatbotModel>()
    inner class ChatViewHolder(bind: MessageLayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        val sender = bind.tvSender
        val receiver = bind.tvReceiver
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = MessageLayoutBinding.inflate(LayoutInflater.from(context),parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var dataItem = data[position]

        when(dataItem.id) {
            "SEND_ID" -> {
                holder.sender.apply {
                    text = dataItem.message
                    visibility = View.VISIBLE
                }
                holder.receiver.visibility = View.GONE
            }
            "RECEIVE_ID" -> {
                holder.receiver.apply {
                    text = dataItem.message
                    visibility = View.VISIBLE
                }
                holder.sender.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = data.size
    fun insertChat(chat : ChatbotModel) {
        this.data.add(chat)
        notifyItemInserted(data.size)
        notifyDataSetChanged()
    }
}