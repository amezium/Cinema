package com.gerija.cinema.view


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerija.cinema.R
import com.gerija.cinema.model.network.api.ApiFactory
import com.gerija.cinema.model.network.dto.ResultsDto
import com.gerija.cinema.repository.MoviesRepositoryImpl
import com.gerija.cinema.databinding.ActivityMoviewsBinding
import kotlinx.coroutines.launch

class MoviesActivity : AppCompatActivity(), MoviesAdapter.ItemClickListener {
    lateinit var binding: ActivityMoviewsBinding
    private val repository = MoviesRepositoryImpl(ApiFactory.create())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repository.getTopMovies().onSuccess {
                startAdapter(it.results)
                binding.progressBarId.visibility = View.GONE
            }
        }
    }

    private fun startAdapter(list: List<ResultsDto>) {
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MoviesAdapter(list, this)
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(this@MoviesActivity, 2)
    }

    override fun onBackPressed() {
        finishAffinity()
    }


    override fun onClick(id: Int) {
        val intent = Intent(this@MoviesActivity, DescriptionActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

}