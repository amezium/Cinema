package com.gerija.cinema.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.gerija.cinema.databinding.ActivityDescriptionBinding
import com.gerija.cinema.model.network.api.ApiFactory
import com.gerija.cinema.model.network.dto.DescriptionContainerDto
import com.gerija.cinema.repository.MoviesRepositoryImpl
import com.squareup.picasso.Picasso
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.launch


class DescriptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityDescriptionBinding
    private val repository = MoviesRepositoryImpl(ApiFactory.create())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            getDetails()
        }
    }

    private suspend fun getDetails(){
        val i = intent.getIntExtra("id", 0)

        //получаю описание фильма
        repository.getMoviesDetails(i).onSuccess {
            setContent(it)
            setVisibility()
        }

        //получаю и устанавливаю видео ролик к фильму
        repository.getVideo(i).onSuccess {
            lifecycle.addObserver(binding.youtubePlayerView)

            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    it.results.forEach { it1 ->
                        youTubePlayer.cueVideo(it1.key, 0f)
                    }
                }
            })
        }
    }


    /**
     * Устанавливаю во view полученные данные с описанием фильма
     */
    private fun setContent(response: DescriptionContainerDto) = with(binding){
        val path = "https://image.tmdb.org/t/p/w500"
        Picasso.get().load(path+response.backdrop_path).into(imageBanner)
        detailsTitle.text = response.title
        detailsData.text = response.release_date
        detailsOverview.text = response.overview
        detailsScore.text = response.vote_average.toString()
          }

    /**
     * Делаю видимыми view элементы
     */
    private fun setVisibility() = with(binding){
        detailsTitle.visibility = View.VISIBLE
        headerContainer.visibility = View.VISIBLE
        bodyContainer.visibility = View.VISIBLE
        binding.progressBId.visibility = View.GONE
    }
}