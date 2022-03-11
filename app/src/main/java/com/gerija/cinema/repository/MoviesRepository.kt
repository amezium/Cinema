package com.gerija.cinema.repository

import com.gerija.cinema.model.network.dto.DescriptionContainerDto
import com.gerija.cinema.model.network.dto.MoviesContainerDto
import com.gerija.cinema.model.network.dto.VideoContainerDto


interface MoviesRepository {

    suspend fun getTopMovies():Result<MoviesContainerDto>

    suspend fun getMoviesDetails(id: Int):Result<DescriptionContainerDto>

    suspend fun getVideo(id: Int): Result<VideoContainerDto>
}