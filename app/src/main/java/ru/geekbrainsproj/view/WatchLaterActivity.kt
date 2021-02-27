package ru.geekbrainsproj.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.geekbrainsproj.WatchLaterRecyclerAdapter
import ru.geekbrainsproj.databinding.WatchLaterActivityBinding
import ru.geekbrainsproj.view_model.WatchLaterViewModel


class WatchLaterActivity : AppCompatActivity() {

    private val viewModel: WatchLaterViewModel by lazy { ViewModelProvider(this).get(WatchLaterViewModel::class.java) }

    private lateinit var recyclerAdapter: WatchLaterRecyclerAdapter
    lateinit var binding: WatchLaterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WatchLaterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()
    }


    private fun initRecycler() {
        binding.recyclerV.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerAdapter = WatchLaterRecyclerAdapter(emptyList())
        binding.recyclerV.adapter = recyclerAdapter

        viewModel.liveData.observe(this, Observer {
            recyclerAdapter.setData(it)
            binding.recyclerV.adapter = recyclerAdapter
        })

    }

}