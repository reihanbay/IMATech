package com.ima.myhealthydiary.smartdiagnosis

import android.R
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ima.myhealthydiary.databinding.ActivitySmartDiagnosisBinding
import com.ima.myhealthydiary.smartdiagnosis.Constants.RECEIVE_ID
import com.ima.myhealthydiary.smartdiagnosis.Constants.SEND_ID
import com.ima.myhealthydiary.utils.api.ApiClient
import com.ima.myhealthydiary.utils.api.service.ChatService
import kotlinx.coroutines.*


class SmartDiagnosisActivity : AppCompatActivity() {
    private lateinit var bind: ActivitySmartDiagnosisBinding
    private lateinit var viewModel: SmartChatBotViewModel
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var adapter: ChatAdapter
    private lateinit var adapterChoice: ChoiceAdapter
    var type : String = ""
    var answer : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivitySmartDiagnosisBinding.inflate(LayoutInflater.from(this))
        setContentView(bind.root)
        viewModel = ViewModelProvider(this).get(SmartChatBotViewModel::class.java)
        val service = ApiClient.getApiClient(this)?.create(ChatService::class.java)
        if (service != null) {
            viewModel.setService(service)
        }
        customMessage("Hallo, Selamat Datang di diagnosis pintar")
        viewModel.postChat("Hallo")
        liveData()
        recyclerView()


    }
    private fun recyclerView(){
        adapter = ChatAdapter(this)
        bind.rvChatbot.adapter = adapter
        bind.rvChatbot.layoutManager = LinearLayoutManager(this)
    }

    private fun changedSheet() {
        when(type) {
            "multiple" -> {
                val arrChoice = answer.split(",")
                bind.gvAnswer.visibility = View.VISIBLE
                bind.etAnswerSmart.visibility = View.GONE
                adapterChoice = ChoiceAdapter(this)
                adapterChoice.dataMenu = arrChoice
                bind.gvAnswer.layoutManager =
                    LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
                bind.gvAnswer.adapter = adapterChoice
                adapterChoice.setOnClick(object : ChoiceAdapter.OnItemClick {
                    override fun OnItemClicked(message: String) {
                        sendMessage(message)
                        viewModel.postChat(message)
                    }
                })
            }
            "freetext" -> {
                bind.gvAnswer.visibility = View.GONE
                bind.etAnswerSmart.visibility = View.VISIBLE
                val message = bind.etAnswerSmart.text.toString()
                bind.etAnswerSmart.setOnEditorActionListener(
                    OnEditorActionListener { _, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event != null && event.action === KeyEvent.ACTION_DOWN && event.keyCode === KeyEvent.KEYCODE_ENTER) {
                            if (event == null || !event.isShiftPressed) {
                                // the user is done typing
                                sendMessage(message)
                                viewModel.postChat(message)
                                return@OnEditorActionListener true // consume.
                            }
                        }
                        false // pass on to other listeners.
                    }
                )
            }
        }
    }
    private fun sendMessage(message: String) {
        if (message.isNotEmpty()) {
            bind.etAnswerSmart.setText("")
            adapter.insertChat(ChatbotModel(message, RECEIVE_ID))
            bind.rvChatbot.scrollToPosition(adapter.itemCount - 1)
        }
    }
    private fun customMessage(message: String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                adapter.insertChat(ChatbotModel(message, SEND_ID))
                bind.rvChatbot.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    fun liveData() {
        viewModel.isSuccessLiveData.observe(this, Observer {
            if (it) {
                Log.d("Success TAG", "liveData: $it")
            } else {
                Log.d("Success TAG", "liveData: $it")
            }
        })

        viewModel.responseLiveData.observe(this, {
            Log.d("data TAG", "liveData: $it")
            customMessage(it.question)
            answer = it.answer
            type = it.type
            changedSheet()
        })
    }
}